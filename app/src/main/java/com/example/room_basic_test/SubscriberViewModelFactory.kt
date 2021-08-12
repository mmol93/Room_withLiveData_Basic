package com.example.room_basic_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.room_basic_test.database.SubscribeRepository
import java.lang.IllegalArgumentException

// 생성자로써 SubscriberRepository의 인스턴스 값 초기화 하고 반환해준다
class SubscriberViewModelFactory(private val repository: SubscribeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // SubscribeViewModel가 modelClass를 상속받는 클래스(또는 인터페이스)인지 확인
        if (modelClass.isAssignableFrom(SubscribeViewModel::class.java)){
            return SubscribeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}