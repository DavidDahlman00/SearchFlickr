package com.example.searchflickir.network

import kotlin.reflect.KProperty

object FlickrApi {
    val retrofitService : FlickrApiService by lazy {
        retrofit.create(FlickrApiService::class.java)}
}

