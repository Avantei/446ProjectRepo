package com.example.mealplanner.ui.groupActivity.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealplanner.data.UserRepository

class MembersViewModel : ViewModel() {
    private val repo: UserRepository = UserRepository()
    private val _groupMembers = MutableLiveData<List<String>>(listOf())

    val groupMembers: LiveData<List<String>> = _groupMembers

    init {
        getMembers()
        println("GROUP MEMBERS:" + groupMembers)
    }

    private fun getMembers() {
        val result = repo.getGroupMembers(null)
        this._groupMembers.value = result
    }
}