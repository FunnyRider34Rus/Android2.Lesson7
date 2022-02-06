package com.example.lesson7.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val city: City = getDefaultCity(),
    val temperature: Int = 0,
    val feelsLike: Int = 0,
    val condition: String = "sunny",
    val icon: String = "svg"
) : Parcelable

fun getDefaultCity() = City("Москва", 55.755826, 37.617299900000035)
