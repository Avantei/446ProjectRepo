package com.example.mealplanner.ui.eventDetailActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.UserRepository
import com.example.mealplanner.ui.user.Group
import com.example.mealplanner.ui.user.UserViewModel


class EventDetailViewModel : ViewModel() {
    // Create repository for getting data
    private val repo: UserRepository = UserRepository()
    // Create Mutable and Live data for view
    private val _decisionName = MutableLiveData<String>()
    private val _decisionImageId = MutableLiveData<Int>()
    val decisionName = _decisionName
    val decisionImageId = _decisionImageId

    // Retrieve data on create
    init {
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