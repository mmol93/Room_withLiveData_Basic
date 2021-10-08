package com.example.room_basic_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_basic_test.adapter.MainRecyclerAdapter
import com.example.room_basic_test.databinding.ActivityMainBinding
import com.example.room_basic_test.subscriber.Subscriber
import com.example.room_basic_test.subscriber.SubscriberDatabase
import com.example.room_basic_test.subscriber.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binder : ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var mainAdapter : MainRecyclerAdapter
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

        // recyclerView에 들어갈 데이터들을 초기화
        initRecyclerView()
    }

    private fun initRecyclerView(){
        binder.subscribeRecyclerView.layoutManager = LinearLayoutManager(this)
        mainAdapter = MainRecyclerAdapter { selectedItem : Subscriber -> itemClickListener(selectedItem) }
        binder.subscribeRecyclerView.adapter = mainAdapter
        displaySubscriberList()
    }

    fun displaySubscriberList(){
        subscriberViewModel.getAllSubscribers().observe(this, Observer {
            // recyclerView에 데이터를 set하고 업데이트를 한다
            mainAdapter.setList(it)
            mainAdapter.notifyDataSetChanged()
        })
    }

    fun itemClickListener(subscriber: Subscriber){
        subscriberViewModel.initUpdateOrDelete(subscriber)
    }
}