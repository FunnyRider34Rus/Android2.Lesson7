package com.example.lesson7.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lesson7.App.Companion.getHistoryDao
import com.example.lesson7.room.LocalRepositoryHistory
import com.example.lesson7.room.LocalRepositoryHistoryImpl

class HistoryViewModel(val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
                       private val historyRepository: LocalRepositoryHistory = LocalRepositoryHistoryImpl(getHistoryDao())
) : ViewModel()  {
    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }
}