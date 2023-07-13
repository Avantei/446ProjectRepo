package com.example.mealplanner.data

import com.example.mealplanner.mock.MockUserSource
import com.example.mealplanner.ui.eventDetailActivity.RsvpGroupMember
import com.example.mealplanner.ui.groupActivity.events.Event

class UserRepository {
    private lateinit var groups: MutableList<String>
    private lateinit var groupMembers: MutableList<String>
    private lateinit var rsvpGroupMembers: MutableList<RsvpGroupMember>
    private lateinit var events: MutableList<Event>
    private lateinit var username: String
    private var isVegetarian = false
    private var isVegan = false
    private var isGlutenFree = false
    private var isDairyFree = false
    private var isHalal = false
    private var isKosher = false

    init {
        mock()
    }

    fun addGroup(name: String): Boolean {
        groups.add(name)
        return true
    }

    fun getGroups(): List<String> {
        return groups.toList()
    }

    fun getGroupMembers(groupName: String?): List<String>? {
        return groupMembers.toList()
    }

    fun getRsvpGroupMembers(): List<RsvpGroupMember>? {
        return rsvpGroupMembers.toList()
    }

    fun addEvent(name: String, time: String, location: String): Boolean {
        events.add(Event(name, time, location))
        // Todo validation checks on event being added
        return true
    }

    fun getEvents(): List<Event> {
        return events.toList()
    }
    fun getIsVegetarian() = isVegetarian
    fun getIsVegan() = isVegan
    fun getIsGlutenFree() = isGlutenFree
    fun getIsDairyFree() = isDairyFree
    fun getIsHalal() = isHalal
    fun getIsKosher() = isKosher
    fun getUsername() = username

    private fun mock() {
        val ds = MockUserSource()
        username = ds.getUsername()

        groups = ds.getGroups().toMutableList()
        groupMembers = ds.getGroupMembers(null).toMutableList()
        events = ds.getEvents().toMutableList()

        rsvpGroupMembers = ds.getRsvpGroupMembers().toMutableList()

        isVegetarian = ds.getIsVegetarian()
        isVegan = ds.getIsVegan()
        isGlutenFree = ds.getIsGlutenFree()
        isDairyFree = ds.getIsDairyFree()
        isHalal = ds.getIsHalal()
        isKosher = ds.getIsKosher()
    }
}