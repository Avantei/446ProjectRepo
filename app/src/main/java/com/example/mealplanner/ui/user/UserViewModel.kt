package com.example.mealplanner.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.UserRepository

data class Group(
    val name: String
)

class UserViewModel : ViewModel() {
    // Create repository for getting data
    private val repo: UserRepository = UserRepository()
    // Create Mutable and Live data for view
    private val _groups = MutableLiveData<List<Group>>(listOf())
    val groups: LiveData<List<Group>> = _groups

    // Retrieve data on create
    init {
        getGroups()
    }
    
    fun addGroup(name: String) {
        val result = repo.addGroup(name)
        if (result) {
            getGroups()
        }
    }

    fun getGroups() {
        val result = repo.getGroups()
        this._groups.postValue(result.map { Group(it) })
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    return UserViewModel() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}