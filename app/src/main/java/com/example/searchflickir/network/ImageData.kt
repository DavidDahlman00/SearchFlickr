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

data class PhotosSearchResponse(
    val photos: PhotosMetaData
)

data class PhotosMetaData(
    val page: Int,
    val photo: List<ImageData>
)