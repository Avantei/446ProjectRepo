package com.example.mealplanner.mock

class MockUserSource {
    private val _username = "Jane Doe"
    fun getGroups() = listOf("Group A", "Group B")
    fun getUsername() = _username
    fun getIsVegetarian() = false
    fun getIsVegan() = true
    fun getIsGlutenFree() = true
    fun getIsDairyFree() = true
    fun getIsHalal() = false
    fun getIsKosher() = false
}