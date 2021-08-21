package com.example.room_basic_test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.database.SubscriberRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: SubscriberRepository) : ViewModel() {
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    val saveOrUpdateText = MutableLiveData<String>()
    val deleteOrClearText = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    init {
        saveOrUpdateText.value = "save"
        deleteOrClearText.value = "clear"
    }

    // when item is clicked, it's called
    fun updateOrDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        subscriberToUpdateOrDelete = subscriber
        isUpdateOrDelete = true
        saveOrUpdateText.value = "update"
        deleteOrClearText.value = "delete"
    }

    fun getSubscriber() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }

    fun saveUpdate(){
        if (inputEmail.value == null || inputName.value == null){
            return
        }

        // update
        if (isUpdateOrDelete){
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!

            viewModelScope.launch {
                repository.update(subscriberToUpdateOrDelete)
            }
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false

            saveOrUpdateText.value = "save"
            deleteOrClearText.value = "clear"
        }

        // insert
        else{
            val name = inputName.value!!
            val email = inputEmail.value!!

            viewModelScope.launch {
                repository.insert(Subscriber(0, name, email))
            }

            inputName.value = null
            inputEmail.value = null
        }
    }

    fun deleteClear(){
        // delete
        if (isUpdateOrDelete){
            viewModelScope.launch {
                inputName.value = null
                inputEmail.value = null
                isUpdateOrDelete = false
                repository.delete(subscriberToUpdateOrDelete)

                saveOrUpdateText.value = "save"
                deleteOrClearText.value = "clear"
            }
        }
        // clear
        else{
            viewModelScope.launch {
                repository.clear()
            }
        }
    }
}