package com.example.searchflickir.network

import com.squareup.moshi.Json

data class ImageData(
    val id: String,
    val server: String,
    val secret: String,
    val title: String
){
    fun getImageUrl(): String {
        return "https://live.staticflickr.com/${server}/${id}_${secret}.jpg"
    }
}
