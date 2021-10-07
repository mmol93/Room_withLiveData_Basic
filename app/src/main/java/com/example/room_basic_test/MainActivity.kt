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
    private lateinit var mainAdapter : MainRecyclerAdapter
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

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        })
    }
    // 리사이클러 뷰를 초기화
    private fun initRecyclerView(){
        binder.subscribeRecyclerView.layoutManager = LinearLayoutManager(this)

        mainAdapter = MainRecyclerAdapter{selectedItem : Subscriber -> listItemClicked(selectedItem) }
        binder.subscribeRecyclerView.adapter = mainAdapter

        displaySubscriberList()
    }
    private fun displaySubscriberList(){
        // getSubscribers() 함수에서 반납되는 변수 값을 감시
        subscriberViewModel.getSubscribers().observe(this, Observer {
            Log.d("TAG", it.toString())
            // 데이터를 넣는다(최소 생성에 무조건 observe를 한 번 실시하기 때문에 이렇게 사용 가능
            mainAdapter.setList(it)
            // recyclerView 안의 데이터가 변경되었음을 알린다
            mainAdapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(subscriber:Subscriber){
        Toast.makeText(this, "selected item is ${subscriber.name}", Toast.LENGTH_SHORT).show()
        Log.d("TAG", "selected item is : ${subscriber.name}")

        subscriberViewModel.initUpdateOrDelete(subscriber)
    }
}