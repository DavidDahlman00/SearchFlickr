package com.example.searchflickir.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchflickir.MockedData
import com.example.searchflickir.network.FlickrApi
import com.example.searchflickir.network.ImageData
import com.example.searchflickir.network.PhotosSearchResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

enum class FlickrApiStatus { LOADING, ERROR, DONE }

class MainViewModel: ViewModel() {

    private val _loadingstatus = MutableLiveData<FlickrApiStatus>()
    val loadingstatus: LiveData<FlickrApiStatus> = _loadingstatus

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _photos = MutableLiveData<List<ImageData>>()
    val photos: LiveData<List<ImageData>> = _photos
    val sType = object : TypeToken<List<ImageData>>() { }.type
    //val otherList = gson.fromJson<List<String>>(jsonString, sType)
    init {
        Log.d("run init", "yes")

       // getFlickrPhotos()
        _photos.value = Gson().fromJson<List<ImageData>>(MockedData.json, sType)

    }

    private fun getFlickrPhotos(searchText: String) {
        _loadingstatus.value = FlickrApiStatus.LOADING
        CoroutineScope(IO).launch {
            val listResult : PhotosSearchResponse
            try {
                listResult  = FlickrApi.retrofitService.getPhotos(text = searchText)
                val photosList =  listResult.photos.photo.map{ photo ->
                    ImageData(id = photo.id, server = photo.server, secret = photo.secret, title = photo.title)}
                setSuccessResult(photosList)
            }catch (e: Exception) {
                Log.d("errror", e.message.toString())
                setFailResult(e.message.toString())
            }

        }
    }

    private suspend fun setSuccessResult(data: List<ImageData>) {
        withContext(Main){
            _status.value =  "Success"
            _loadingstatus.value = FlickrApiStatus.DONE
            _photos.postValue(data)
        }
    }

    private suspend fun setFailResult(string: String) {
        withContext(Main){
            _status.value = "Failure: $string"
            _loadingstatus.value = FlickrApiStatus.ERROR
        }
    }

    fun searchForNewPhotos(searchText: String){
        searchTag = searchText
        getFlickrPhotos(searchText)
    }


    companion object{
        var BASE_URL = "http://api.flickr.com/services/rest/"
        lateinit var focusedImage: ImageData
        lateinit var searchTag: String
    }
}