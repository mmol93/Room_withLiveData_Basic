package com.example.room_basic_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.room_basic_test.databinding.ActivityMainBinding
import com.example.room_basic_test.subscriber.SubscriberDatabase
import com.example.room_basic_test.subscriber.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binder : ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // room 데이터 베이스 생성
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        // dao를 매개 변수로 한 repository 생성
        val repository = SubscriberRepository(dao)
        // repository를 매개 변수로 한 factory 생성
        val factory = SubscriberViewModelFactory(repository)

        // viewModel 정의
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        // xml 파일과 viewModel 연결하기
        binder.myViewModel = subscriberViewModel
        // viewModel의 lifeCycle 지정하기
        binder.lifecycleOwner = this
    }
}