package com.example.room_basic_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_basic_test.adapter.MainRecyclerAdapter
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.database.SubscriberDatabase
import com.example.room_basic_test.database.SubscriberRepository
import com.example.room_basic_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binder : ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberViewModelFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binder.myViewModel = subscriberViewModel
        binder.lifecycleOwner = this

        initRecyclerView()
    }
    // 리사이클러 뷰를 초기화
    private fun initRecyclerView(){
        binder.subscribeRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscriberList()
    }
    private fun displaySubscriberList(){
        // getSubscribers() 함수에서 반납되는 변수 값을 감시
        subscriberViewModel.getSubscribers().observe(this, Observer {
            Log.d("TAG", it.toString())
            val mainAdapter = MainRecyclerAdapter(it){selectedItem : Subscriber ->
            listItemClicked(selectedItem)
        }
            binder.subscribeRecyclerView.adapter = mainAdapter
        })
    }

    private fun listItemClicked(subscriber:Subscriber){
        Toast.makeText(this, "selected item is ${subscriber.name}", Toast.LENGTH_SHORT).show()
        Log.d("TAG", "selected item is : ${subscriber.name}")
    }
}