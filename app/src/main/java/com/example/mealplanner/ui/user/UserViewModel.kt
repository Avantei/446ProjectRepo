package com.example.mealplanner.ui.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

data class Group(
    val name: String
)

class UserViewModel : ViewModel() {
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _groups = MutableLiveData<List<Group>>(listOf(Group("Group A")
        , Group("Group B"), Group("Group C")))
    val groups: LiveData<List<Group>> = _groups

    fun setText(content: String) {
        _text.value = content
    }

    fun addGroup(name: String) {
        Log.d("PETER", name)
        val list = _groups.value?.toMutableList()
        list?.add(Group(name))
        this._groups.postValue(list)
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