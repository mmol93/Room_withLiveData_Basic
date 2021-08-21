package com.example.room_basic_test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.room_basic_test.database.SubscriberRepository
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val repository: SubscriberRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            // 결과로는 SubscribeViewModel을 반환해준다
            // 즉, Factory의 역할은 SubscribeViewModel를 초기화하고 반환해주는 것이다
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}