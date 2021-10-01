package com.example.room_basic_test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.database.SubscriberRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository):ViewModel() {
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    val saveUpdateButtonText = MutableLiveData<String>()
    val clearDeleteButtonText = MutableLiveData<String>()

    init {
        saveUpdateButtonText.value = "save"
        clearDeleteButtonText.value = "clear"
    }
    // 데이터 베이스를 추가 or 업데이트
    fun saveUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        insert(Subscriber(0, name, email))
    }
    // 데이터 베이스 일부를 삭제 or 전부 삭제
    fun clearDelete(){
        clear()
    }

    // ↓ repository에 있는 메서드 호출하는 영역
    fun insert(subscriber: Subscriber){
        viewModelScope.launch {
            repository.insert(subscriber)
        }
    }

    fun clear(){
        viewModelScope.launch {
            repository.clear()
        }
    }

    fun update(subscriber: Subscriber){
        viewModelScope.launch {
            repository.update(subscriber)
        }
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }

    fun getSubscribers() = liveData<List<Subscriber>> {
        repository.subscribers.collect {
            emit(it)
        }
    }
}