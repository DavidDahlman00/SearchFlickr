package com.example.searchflickir.network

import com.example.searchflickir.main.MainViewModel
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val BASE_URL = MainViewModel.BASE_URL
private const val key = ""//Enter private key
private val tag = MainViewModel.searchTag


val okHttpClient = OkHttpClient()
    .newBuilder()
    .addInterceptor(RequestInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

interface FlickrApiService {
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&text=fish&safe_search=1&per_page=20&api_key=$key")
    suspend fun getPhotos() :  PhotosSearchResponse
}

object RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("Outgoing request to ${request.url()}")
        return chain.proceed(request)
    }
}