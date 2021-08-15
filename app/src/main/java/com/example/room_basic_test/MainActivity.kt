package com.example.room_basic_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_basic_test.adapter.MainRecyclerAdapter
import com.example.room_basic_test.database.SubscribeRepository
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.database.SubscriberDatabase
import com.example.room_basic_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binder : ActivityMainBinding
    private lateinit var viewModel: SubscribeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // Room 데이터베이스 정의
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        // Repository 생성
        val repository = SubscribeRepository(dao)
        // viewModelFactory로 viewModel 초기화 하기
        val viewModelFactory = SubscriberViewModelFactory(repository)

        // viewModel과 lifecycle 묶기
        viewModel = ViewModelProvider(this, viewModelFactory).get(SubscribeViewModel::class.java)
        binder.myViewModel = viewModel
        binder.lifecycleOwner = this

        initRecyclerView()

        viewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initRecyclerView(){
        // 리사이클로 기본 형태를 정의(layoutManager)
        binder.subscribeRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscriber()
    }

    private fun displaySubscriber(){
        viewModel.saveSubscribers().observe(this, Observer {
            binder.subscribeRecyclerView.adapter = MainRecyclerAdapter(it){selectedItem : Subscriber ->
                listItemClicked(selectedItem)
            }
        })
    }

    private fun listItemClicked(subscriber: Subscriber){
        Toast.makeText(this, "item selected: ${subscriber.name}", Toast.LENGTH_LONG).show()
        viewModel.initUpdateAndDelete(subscriber)
    }
}