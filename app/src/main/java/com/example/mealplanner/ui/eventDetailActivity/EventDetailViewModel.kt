package com.example.mealplanner.ui.eventDetailActivity

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.R
import com.example.mealplanner.data.UserRepository
import com.example.mealplanner.ui.locationFragment.LocationSuggestion


class EventDetailViewModel : ViewModel() {
    // Create repository for getting data
    private val repo: UserRepository = UserRepository()
    // Create Mutable and Live data for view
    private val _decisionName = MutableLiveData<String>()
    private val _decisionImageId = MutableLiveData<Int>()
    private val _locationList: MutableLiveData<List<LocationSuggestion>> = MutableLiveData(
        listOf()
    )
    private val _rsvpGroupMembers = MutableLiveData<List<RsvpGroupMember>>(listOf())
    val decisionName: LiveData<String> = _decisionName
    val decisionImageId: LiveData<Int> = _decisionImageId
    val locationList: LiveData<List<LocationSuggestion>> = _locationList
    val rsvpGroupMembers: LiveData<List<RsvpGroupMember>> = _rsvpGroupMembers
    var availableDate: String? = null //dd/MM/yyyy
        private set
    var availableStartTime: String? = null //24h
        private set
    var availableEndTime: String? = null //24h
        private set

    // Retrieve data on create
    init {
        // TODO: remove mock data
        _decisionName.value = "placeholder"
        _decisionImageId.value = R.drawable.default_restaurant_image
        getRsvpGroupMembers()
        Log.d("TODO", "init rsvpGroupMembers:")
        rsvpGroupMembers.value?.forEach { member -> Log.d("TODO", "init rsvpGroupMember - ${member.name}")}
    }

    private fun getRsvpGroupMembers() {
        val result = repo.getRsvpGroupMembers()
        this._rsvpGroupMembers.value = result
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