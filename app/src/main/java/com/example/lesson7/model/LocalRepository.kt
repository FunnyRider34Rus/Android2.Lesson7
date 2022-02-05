package com.example.lesson7.model

interface LocalRepository {
    fun getWorldCities(): List<Weather>
    fun getRussianCities(): List<Weather>
}