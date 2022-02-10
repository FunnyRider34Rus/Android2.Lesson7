package com.example.lesson7.room

import com.example.lesson7.model.Weather

interface LocalRepositoryHistory {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}