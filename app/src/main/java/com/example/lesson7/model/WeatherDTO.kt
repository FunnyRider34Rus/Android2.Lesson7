package com.example.lesson7.model

data class WeatherDTO(val fact: FactDTO?)

data class FactDTO(
    val temp: Int?,
    val condition: String?,
    val feels_like:Int?,
    val icon:String?
)
