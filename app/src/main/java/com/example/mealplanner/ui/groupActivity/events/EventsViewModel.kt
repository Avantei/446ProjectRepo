package com.example.mealplanner.ui.groupActivity.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.UserRepository
import com.example.mealplanner.ui.userActivity.Group

data class Event(
    val name: String,
    val time: String,
    val location: String,
)

class EventsViewModel : ViewModel() {
    private val repo: UserRepository = UserRepository()
    private val _events = MutableLiveData<List<Event>>(listOf())

    val events: LiveData<List<Event>> = _events

    init {
        getEvents()
    }

    private fun getEvents() {
        val result = repo.getEvents()
        this._events.value = result
    }

    fun addEvent(name: String) {
        val result = repo.addEvent(name, "Unknown", "Unknown")
        if (result) {
            getEvents()
        }
    }
}