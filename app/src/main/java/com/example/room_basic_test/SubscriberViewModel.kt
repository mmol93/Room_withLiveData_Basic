package com.example.room_basic_test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room_basic_test.subscriber.Subscriber
import com.example.room_basic_test.subscriber.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(val repository: SubscriberRepository) : ViewModel() {
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()

    val saveUpdateButtonText = MutableLiveData<String>()
    val clearDeleteButtonText = MutableLiveData<String>()

    init {
        saveUpdateButtonText.value = "save"
        clearDeleteButtonText.value = "clear"
    }

    fun saveUpdate(){
        // 텍스트에 입력한 값을 등록하기
        insert(Subscriber(0, inputName.value!!, inputEmail.value!!))
    }

    fun insert(subscriber: Subscriber){
        viewModelScope.launch {
            repository.insert(subscriber)
            Log.d("TAG", "insert ${subscriber.name}")
        }
    }

    fun clearAllDelete(){
        viewModelScope.launch {
            repository.clear()
        }
    }
}