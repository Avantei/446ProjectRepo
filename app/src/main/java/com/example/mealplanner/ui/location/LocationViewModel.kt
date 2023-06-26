package com.example.mealplanner.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LocationViewModel : ViewModel() {

    var locationlist: MutableLiveData<MutableList<LocationSuggestion>> = MutableLiveData(
        mutableListOf())

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {
                if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
                    return LocationViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}