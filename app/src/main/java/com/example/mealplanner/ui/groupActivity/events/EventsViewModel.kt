package com.example.mealplanner.ui.groupActivity.events

import android.location.Address
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.UserRepository
import com.example.mealplanner.data.model.LoggedInUser
import com.example.mealplanner.ui.userActivity.Group

data class Event(
    val name: String,
    val time: String, // leaving as a string, this will change as we change types of time options
    val location: String, //Address,
    //val timeFirst: Boolean,
    //val eventComplete: Boolean, // when event has been voted on and set
    //val eventBill: Map<LoggedInUser, List<Int>>
)

// Voting can be in the form of Key, Votes ?
data class EventVoting(
    val locationSuggestions: Map<Address, List<LoggedInUser>>,
    val timeSuggestions: Map<String, List<LoggedInUser>>,
    val rsvpInformation: Map<LoggedInUser, Any>
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