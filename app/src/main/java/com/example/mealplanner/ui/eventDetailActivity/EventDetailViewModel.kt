package com.example.mealplanner.ui.eventDetailActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.R
import com.example.mealplanner.ui.locationFragment.LocationSuggestion


class EventDetailViewModel : ViewModel() {
    // Create repository for getting data
//    private val repo: UserRepository = UserRepository()
    // Create Mutable and Live data for view
    private val _decisionName = MutableLiveData<String>()
    private val _decisionImageId = MutableLiveData<Int>()
    private val _locationList = MutableLiveData<MutableList<LocationSuggestion>>()
    val decisionName = _decisionName
    val decisionImageId = _decisionImageId
    val locationList = _locationList
    var availableDate: String? = null //dd/MM/yyyy
        private set
    var availableStartTime: String? = null //24h
        private set
    var availableEndTime: String? = null //24h
        private set

    // Retrieve data on create
    init {
        _decisionName.value = "placeholder"
        _decisionImageId.value = R.drawable.default_restaurant_image
    }


    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {
                if (modelClass.isAssignableFrom(EventDetailViewModel::class.java)) {
                    return EventDetailViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    fun setAvailableDate(AvailableDate: String) {
        availableDate = AvailableDate
    }

    fun setAvailableStartTime(AvailableStartTime: String) {
        availableStartTime = AvailableStartTime
    }

    fun setAvailableEndTime(AvailableEndTime: String) {
        availableEndTime = AvailableEndTime
    }


}