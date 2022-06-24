package com.example.searchflickir.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchflickir.MockedData
import com.example.searchflickir.network.FlickrApi
import com.example.searchflickir.network.ImageData
import kotlinx.coroutines.launch

enum class FlickrApiStatus { LOADING, ERROR, DONE }

class MainViewModel: ViewModel() {

    private val _loadingstatus = MutableLiveData<FlickrApiStatus>()
    val loadingstatus: LiveData<FlickrApiStatus> = _loadingstatus

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _photos = MutableLiveData<List<ImageData>>()
    val photos: LiveData<List<ImageData>> = _photos

    init {
        Log.d("run init", "yes")
        BASE_URL = setUrl()
        getFlickirPhotos()
        _photos.value = MockedData.data
    }

    private fun getFlickirPhotos() {
        viewModelScope.launch {
            _loadingstatus.value = FlickrApiStatus.LOADING

            try {

                val listResult = FlickrApi.retrofitService.getPhotos()
                _status.value =  "Success: ${listResult.size} Mars photos retrieved"
                _loadingstatus.value = FlickrApiStatus.DONE

            }catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                _loadingstatus.value = FlickrApiStatus.ERROR
            }

        }
    }



    private fun setUrl(): String{
        val firstPart = "http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key="
        val key = ""//Enter your api-key here
        val secPart = "&tags=yokota+air+base&safe_search=1&per_page=20"
        Log.d("getRequest is ", "$firstPart$key$secPart")
        return  "$firstPart$key$secPart"

    }

    companion object{
       lateinit var BASE_URL: String
    }
}