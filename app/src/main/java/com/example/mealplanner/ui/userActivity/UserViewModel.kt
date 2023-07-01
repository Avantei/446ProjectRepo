package com.example.mealplanner.ui.userActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mealplanner.data.UserRepository
import com.example.mealplanner.data.model.LoggedInUser
import com.example.mealplanner.ui.groupActivity.events.Event

data class Group(
    val name: String,
    //val members: List<String>,
    //val events: List<Event>,
    //val groupBill: Map<String, List<Int>>, // unsure if we want to make group bill a data class
    //val groupOwner: LoggedInUser, // could also swap to a string for just userID or whatever auth
)

class UserViewModel : ViewModel() {
    // Create repository for getting data
    private val repo: UserRepository = UserRepository()
    // Create Mutable and Live data for view
    private val _groups = MutableLiveData<List<Group>>(listOf())
    val groups: LiveData<List<Group>> = _groups
    private val _username = MutableLiveData<String>("")
    val username: LiveData<String> = _username
    // Dietary Restriction
    private val _isVegetarian = MutableLiveData<Boolean>(false)
    private val _isVegan = MutableLiveData<Boolean>(false)
    private val _isGlutenFree = MutableLiveData<Boolean>(false)
    private val _isDairyFree = MutableLiveData<Boolean>(false)
    private val _isHalal = MutableLiveData<Boolean>(false)
    private val _isKosher = MutableLiveData<Boolean>(false)
    val isVegetarian: LiveData<Boolean> = _isVegetarian
    val isVegan: LiveData<Boolean> = _isVegan
    val isGlutenFree: LiveData<Boolean> = _isGlutenFree
    val isDairyFree: LiveData<Boolean> = _isDairyFree
    val isHalal: LiveData<Boolean> = _isHalal
    val isKosher: LiveData<Boolean> = _isKosher

    // Retrieve data on create
    init {
        getGroups()
        _username.value = repo.getUsername()
        _isVegetarian.value = repo.getIsVegetarian()
        _isVegan.value = repo.getIsVegan()
        _isGlutenFree.value = repo.getIsGlutenFree()
        _isDairyFree.value = repo.getIsDairyFree()
        _isHalal.value = repo.getIsHalal()
        _isKosher.value = repo.getIsKosher()
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

    // TODO: demo only
    fun changeDisplayName(name: String) {
        _username.value = name
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