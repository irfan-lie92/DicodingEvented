package com.dicodingevent.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicodingevent.data.remote.response.DetailData
import com.dicodingevent.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch


class MainViewModelFinish : ViewModel() {

    private val _listEvents = MutableLiveData<List<DetailData>>()
    val listEvent: LiveData<List<DetailData>> = _listEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findEventVertical()
    }

    fun searchFinishedEvents(active: Int, limit: Int, query: String?) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ApiConfig.getApiService()
                    .getEvents(active = active, limit = limit, query = query)
                _listEvents.value = response.data

            } catch (e: Exception) {
                Log.e(MainViewModel.TAG, "onFailure : ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }

    }

    private fun findEventVertical() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = ApiConfig.getApiService().getEvents(0)

                _listEvents.value = response.data

            } catch (e: Exception) {
                Log.e(MainViewModel.TAG, "onFailure : ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }

    }
}