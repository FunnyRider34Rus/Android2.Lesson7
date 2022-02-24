package com.example.lesson7.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson7.model.LocalDataSource
import com.example.lesson7.model.LocalRepository
import com.example.lesson7.model.Weather

class MainViewModel(private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()): ViewModel() {

    private val localRepository: LocalRepository = LocalDataSource()
    fun getLiveData() = liveDataToObserve
    fun getWeatherFromLocalSourceRus() = getDataFromLocalSource(isRussian = true)
    fun getWeatherFromLocalSourceWorld() = getDataFromLocalSource(isRussian = false)
    private fun getDataFromLocalSource(isRussian: Boolean) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(
                AppState.Success(
                    if (isRussian) localRepository.getRussianCities()
                    else localRepository.getWorldCities()
                )
            )
        }.start()
    }
}