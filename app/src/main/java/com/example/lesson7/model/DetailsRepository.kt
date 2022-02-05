package com.example.lesson7.model

import okhttp3.Callback

interface DetailsRepository {
    fun getWeatherDetailsFromServer(requestLink: String, callback: Callback)
}