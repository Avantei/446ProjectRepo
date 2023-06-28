package com.example.mealplanner.mock

import com.example.mealplanner.ui.groupActivity.events.Event

class MockUserSource {
    private val _username = "Jane Doe"
    fun getGroups() = listOf("Group A", "Group B")
    fun getEvents() = listOf(Event("Dinner", "Waiting for Time", "Mel's Diner"),
    Event("Breakfast", "Thursday, June 29, 2023", "Tim Hortons"))
    fun getGroupMembers(groupName: String?) = listOf("Max", "Peter", "Jas", "Justin", "Matt", "Marwan")
    fun getUsername() = _username
    fun getIsVegetarian() = false
    fun getIsVegan() = true
    fun getIsGlutenFree() = true
    fun getIsDairyFree() = true
    fun getIsHalal() = false
    fun getIsKosher() = false
}