package com.example.room_basic_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room_basic_test.R
import com.example.room_basic_test.databinding.ListItemBinding
import com.example.room_basic_test.subscriber.Subscriber

class MainRecyclerAdapter(private val clickListener:(Subscriber) -> Unit) : RecyclerView.Adapter<MainViewHolder>() {
    val subscriberList = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binder = DataBindingUtil.inflate<ListItemBinding>(inflater, R.layout.list_item, parent, false)
        return MainViewHolder(binder)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding(subscriberList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }

    fun setList(subscribers: List<Subscriber>){
        subscriberList.clear()
        subscriberList.addAll(subscribers)
    }
}

class MainViewHolder(val binder : ListItemBinding):RecyclerView.ViewHolder(binder.root){
    fun binding(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
        binder.nameTextView.text = subscriber.name
        binder.emailTextView.text = subscriber.email
        binder.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}