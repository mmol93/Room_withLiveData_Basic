package com.example.room_basic_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.room_basic_test.database.SubscriberDAO
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

        displaySubscriberList()
    }
    fun displaySubscriberList(){
        subscriberViewModel.getSubscribers().observe(this, Observer {
            Log.d("TAG", it.toString())
        })
    }
}