package com.example.searchflickir.network

object FlickrApi {
    val retrofitService : FlickrApiService by lazy {
        retrofit.create(FlickrApiService::class.java)}
}

