package com.example.lesson7.model

import com.example.lesson7.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    private val weatherApi = Retrofit.Builder() .baseUrl("https://api.weather.yandex.ru/") .addConverterFactory(
        GsonConverterFactory.create( GsonBuilder().setLenient().create()
        )).build().create(WeatherAPI::class.java)

    fun getWeatherDetails(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        weatherApi.getWeather(BuildConfig.API_WEATHER_KEY, lat, lon).enqueue(callback)
    }
}