package com.example.mealplanner.ui.eventDetailActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.R
import com.example.mealplanner.ui.eventDetailActivity.TimePicker.TimePickerData
import com.example.mealplanner.ui.locationFragment.LocationSuggestion


class EventDetailViewModel : ViewModel() {
    // Create repository for getting data
//    private val repo: UserRepository = UserRepository()
    // Create Mutable and Live data for view
    private val _decisionName = MutableLiveData<String>()
    private val _decisionImageId = MutableLiveData<Int>()
    private val _locationList: MutableLiveData<List<LocationSuggestion>> = MutableLiveData(
        listOf()
    )
    private val _availabilitiesList: MutableLiveData<List<TimePickerData>> = MutableLiveData( listOf() )
    val decisionName: LiveData<String> = _decisionName
    val decisionImageId: LiveData<Int> = _decisionImageId
    val locationList: LiveData<List<LocationSuggestion>> = _locationList
    val availabilitiesList: LiveData<List<TimePickerData>> = _availabilitiesList

    // Retrieve data on create
    init {
        // TODO: remove mock data
        _decisionName.value = "placeholder"
        _decisionImageId.value = R.drawable.default_restaurant_image
    }

    private fun updateDecision() {
        // TODO: this is for first demo, later decisionName should be fetched from server
        var highest: LocationSuggestion? = null
        _locationList.value?.forEach {
            if (highest == null || highest!!.votes < it.votes) {
                highest = it
            }
        }
        _decisionName.postValue(if (highest != null) highest!!.name else "")
    }

    fun addVote(index: Int) {
        val list = _locationList.value?.toMutableList()
        list?.get(index)?.votes = list?.get(index)?.votes?.plus(1)!!
        _locationList.value = list.toList()
        updateDecision()
    }

    fun addLocation(name: String) {
        val list = _locationList.value?.toMutableList()
        list?.add(LocationSuggestion(name, 0))
        _locationList.value = list?.toList()
        updateDecision()
    }

    fun addAvailability(date: String, time: String) {
        Log.d("TAG", "in add function: " + date + " " + time)
        val newAvailability = TimePickerData(date, time)
        val updatedList = _availabilitiesList.value?.toMutableList() ?: mutableListOf()
        updatedList.add(newAvailability)
        _availabilitiesList.value = updatedList

//        for (a in _availabilitiesList.value ?: emptyList()) {
//            // Access each availability object
//            Log.d("list", "in add func: " + a.toString())
//        }

    }

    fun submitAvailability() {
        // send _availabilities_list to wherever needed
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



}