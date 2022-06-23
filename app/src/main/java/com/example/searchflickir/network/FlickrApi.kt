package com.example.searchflickir.network

import kotlin.reflect.KProperty

object FlickrApi {
    val retrofitService : FlickirApiService by lazy {
        retrofit.create(FlickirApiService::class.java)}
}

