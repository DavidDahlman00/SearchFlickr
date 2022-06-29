package com.example.searchflickir.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchflickir.MockedData
import com.example.searchflickir.network.FlickrApi
import com.example.searchflickir.network.ImageData
import com.example.searchflickir.network.PhotosSearchResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import java.lang.reflect.Type

enum class FlickrApiStatus { LOADING, ERROR, DONE }

class MainViewModel: ViewModel() {

    private val _loadingStatus = MutableLiveData<FlickrApiStatus>()
    val loadingStatus: LiveData<FlickrApiStatus> = _loadingStatus

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _photos = MutableLiveData<List<ImageData>>()
    val photos: LiveData<List<ImageData>> = _photos
    private val sType: Type = object : TypeToken<List<ImageData>>() { }.type

    init {
        _photos.value = Gson().fromJson<List<ImageData>>(MockedData.json, sType)
    }

    private var job: CoroutineScope? = CoroutineScope(IO)

    private fun getFlickrPhotos(searchText: String) {
        _loadingStatus.value = FlickrApiStatus.LOADING
        job?.launch {
            val listResult : PhotosSearchResponse
            try {
                listResult  = FlickrApi.retrofitService.getPhotos(text = searchText,
                    numImages = numImagesToReturn,
                    minUploadDate = minUploadDate)
                val photosList =  listResult.photos.photo.map{ photo ->
                    ImageData(id = photo.id, server = photo.server, secret = photo.secret, title = photo.title)}
                setSuccessResult(photosList)
            }catch (e: Exception) {
                Log.d("errror", e.message.toString())
                setFailResult(e.message.toString())

            }
        }
    }

    private fun getFlickrPhotosLocal(searchText: String) {
        _loadingStatus.value = FlickrApiStatus.LOADING
        job?.launch {
            val listResult : PhotosSearchResponse
            try {
                listResult  = FlickrApi.retrofitService.getPhotosLocal(text = searchText,
                    numImages = numImagesToReturn,
                    minUploadDate = minUploadDate,
                    latitude = latitude.toString(),
                    longitude = longitude.toString(),
                    radius = radius)
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
            _status.value =  ""
            _loadingStatus.value = FlickrApiStatus.DONE
            _photos.postValue(data)
        }
    }

    private suspend fun setFailResult(string: String) {
        withContext(Main){
            _status.value = "Failure: $string"
            _loadingStatus.value = FlickrApiStatus.ERROR
        }
    }

    fun searchForNewPhotos(searchText: String){
        searchTag = searchText
        if (useLocation){ //&& isLocationAllowed){
            getFlickrPhotosLocal(searchText)
        }else{
            getFlickrPhotos(searchText)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }

    companion object{
        var BASE_URL = "http://api.flickr.com/services/rest/"
        lateinit var focusedImage: ImageData
        lateinit var searchTag: String
        var numImagesToReturn: String = "20"
        var useUploadDate: Boolean = false
        var minUploadDate: String = "1970-01-01"
        var useLocation: Boolean = false
        var latitude: Double = 59.0
        var longitude: Double = 18.0
        var radius: String = "20"
    }
}
