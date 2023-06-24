package com.example.mealplanner.data

class UserRepository {
    private val groups = mutableListOf<String>("Group A", "Group B")

    fun addGroup(name: String): Boolean {
        groups.add(name)
        return true
    }

    fun getGroups(): List<String> {
        return groups.toList()
    }
}