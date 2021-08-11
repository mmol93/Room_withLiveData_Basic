package com.example.room_basic_test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.room_basic_test.database.SubscribeRepository
import com.example.room_basic_test.database.Subscriber
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// SubscribeRepository에서 정의한 suspend 함수들을 해당 viewModel과 묶어서 실행하게 하기
class SubscribeViewModel(private val repository: SubscribeRepository) : ViewModel() {
    // 입력 값에 대한 liveData 설정
    val inputName = MutableLiveData<String?>()
    val inputEmail = MutableLiveData<String?>()

    // 데이터 선택에 따른 버튼 텍스트의 liveData 설정
    val saveUpdateButtonText = MutableLiveData<String>()
    val deleteDeleteAllButtonText = MutableLiveData<String>()

    init {
        saveUpdateButtonText.value = "Save"
        deleteDeleteAllButtonText.value = "Delete All"
    }

    // insert 기능 설정
    // viewModelScope를 사용하는 이유
    // viewModelScope의 경우 해당 viewModel class에 종속되어
    // 해당 viewModel이 삭제되면 viewModelScope도 같이 사라진다
    private fun insertFunction(subscriber: Subscriber){
        viewModelScope.launch {
            repository.insert(subscriber)
        }
    }

    fun insert(){
        val name = inputName.value!!
        val email = inputEmail.value!!

        insertFunction(Subscriber(0, name, email))
        inputName.value = null
        inputEmail.value = null
    }

    fun delete(subscriber: Subscriber){
        viewModelScope.launch {
            repository.delete(subscriber)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun saveSubscribers() = liveData {
        // collect: Flow 인터페이스에서 값을 가져오는 함수
        repository.subscriber.collect {
            emit(it)
        }
    }
}