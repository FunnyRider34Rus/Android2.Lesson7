package com.example.lesson7.utils

import com.example.lesson7.model.FactDTO
import com.example.lesson7.model.Weather
import com.example.lesson7.model.WeatherDTO
import com.example.lesson7.model.getDefaultCity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.fact!!
    return listOf(Weather(getDefaultCity(), fact.temp!!, fact.feels_like!!,
        fact.condition!!, fact.icon!!))
}