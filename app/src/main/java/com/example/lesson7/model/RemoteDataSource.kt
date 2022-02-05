package com.example.lesson7.model

import com.example.lesson7.BuildConfig
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

private const val REQUEST_API_KEY = "X-Yandex-API-Key"

class RemoteDataSource {
    fun getWeatherDetails(requestLink: String, callback: Callback) {
        val builder: Request.Builder = Request.Builder().apply {
            header(REQUEST_API_KEY, BuildConfig.API_WEATHER_KEY)
            url(requestLink)
        }
        OkHttpClient().newCall(builder.build()).enqueue(callback)
    }
}