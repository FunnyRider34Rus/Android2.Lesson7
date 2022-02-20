package com.example.lesson7.room

import com.example.lesson7.model.Weather
import com.example.lesson7.utils.convertHistoryEntityToWeather
import com.example.lesson7.utils.convertWeatherToEntity

class LocalRepositoryHistoryImpl (private val localDataSource: HistoryDao) : LocalRepositoryHistory {

    override fun getAllHistory(): List<Weather> {
        return convertHistoryEntityToWeather(localDataSource.all())
    }

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}