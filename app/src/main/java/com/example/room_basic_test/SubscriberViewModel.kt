package com.example.room_basic_test

import android.util.Log
import androidx.lifecycle.*
import com.example.room_basic_test.subscriber.Subscriber
import com.example.room_basic_test.subscriber.SubscriberRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SubscriberViewModel(val repository: SubscriberRepository) : ViewModel() {
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    // mainActivity.xml의 버튼 이름에 사용될 변수
    val saveUpdateButtonText = MutableLiveData<String>()
    val clearDeleteButtonText = MutableLiveData<String>()

    // 아이템 클릭 시 사용될 변수
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    init {
        saveUpdateButtonText.value = "save"
        clearDeleteButtonText.value = "clear"
    }

    fun initUpdateOrDelete(subscriber: Subscriber){
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        inputName.value = subscriberToUpdateOrDelete.name
        inputEmail.value = subscriberToUpdateOrDelete.email
        saveUpdateButtonText.value = "update"
        clearDeleteButtonText.value = "delete"
    }

    // *** save(insert) 및 update 기능 정의
    fun saveUpdate(){
        if (isUpdateOrDelete){
            // subscriber의 일부를 업데이트할 내용으로 바꾸어 준다
            subscriberToUpdateOrDelete.name = inputName.value!!
            subscriberToUpdateOrDelete.email = inputEmail.value!!

            update(subscriberToUpdateOrDelete)
            isUpdateOrDelete = false
        }else{
            // 텍스트에 입력한 값을 등록하기
            insert(Subscriber(0, inputName.value!!, inputEmail.value!!))
        }
        inputName.value = null
        inputEmail.value = null
        saveUpdateButtonText.value = "save"
        clearDeleteButtonText.value = "clear"
    }

    fun insert(subscriber: Subscriber){
        viewModelScope.launch {
            repository.insert(subscriber)
            Log.d("TAG", "insert ${subscriber.name}")
        }
    }

    fun update(subscriber: Subscriber){
        viewModelScope.launch {
            repository.update(subscriber)
            Log.d("TAG", "update ${subscriber.name}")
        }
    }

    // *** clear 및 delete 기능 정의

    fun clearAllDelete(){
        if (isUpdateOrDelete){
            delete(subscriberToUpdateOrDelete)
            isUpdateOrDelete = false
            inputName.value = null
            inputEmail.value = null
        }else{
            viewModelScope.launch {
                repository.clear()
            }
        }
        saveUpdateButtonText.value = "save"
        clearDeleteButtonText.value = "clear"
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }

    // 구독자 데이터 가져오기기
   fun getAllSubscribers() = liveData<List<Subscriber>> {
        repository.subscribers.collect {
            emit(it)
        }
    }
}