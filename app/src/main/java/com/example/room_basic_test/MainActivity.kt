package com.example.room_basic_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_basic_test.adapter.MainAdapter
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.database.SubscriberDAO
import com.example.room_basic_test.database.SubscriberDatabase
import com.example.room_basic_test.database.SubscriberRepository
import com.example.room_basic_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binder : ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter : MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // DAO
        val dao = SubscriberDatabase.getInstance(applicationContext).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = MainViewModelFactory(repository)

        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        binder.myViewModel = mainViewModel
        binder.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView(){
        binder.subscribeRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter { selectedItem : Subscriber -> itemClickListener(selectedItem) }
        binder.subscribeRecyclerView.adapter = adapter
        displaySubscriberList()
    }

    private fun displaySubscriberList(){
        mainViewModel.getSubscriber().observe(this, Observer {
            Log.d("test", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun itemClickListener(subscriber: Subscriber){
        mainViewModel.updateOrDelete(subscriber)
        Log.d("test","clicked item is ${subscriber.name}")
    }
}