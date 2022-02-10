package com.example.lesson7.viewmodel

import com.example.lesson7.model.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error : Throwable) : AppState()
    object Loading : AppState()
}
