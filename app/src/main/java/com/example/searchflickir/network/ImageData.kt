package com.example.searchflickir.network

import com.squareup.moshi.Json

data class ImageData(
    val id: String,
   // @Json(name = "img_src") val imgSrcUrl: String
    val server: String,
    val secret: String
)
