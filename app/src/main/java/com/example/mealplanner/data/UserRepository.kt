package com.example.mealplanner.data

import com.example.mealplanner.mock.MockUserSource

class UserRepository {
    private lateinit var groups: MutableList<String>
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
        isVegetarian = ds.getIsVegetarian()
        isVegan = ds.getIsVegan()
        isGlutenFree = ds.getIsGlutenFree()
        isDairyFree = ds.getIsDairyFree()
        isHalal = ds.getIsHalal()
        isKosher = ds.getIsKosher()
    }
}