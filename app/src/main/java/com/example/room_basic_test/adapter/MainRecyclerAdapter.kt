package com.example.room_basic_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room_basic_test.R
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.databinding.ListItemBinding

class MainRecyclerAdapter(private val subscriberList : List<Subscriber>, private val clickListener : (Subscriber) -> Unit)
    : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater =LayoutInflater.from(parent.context)
        val binder : ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)

        return MyViewHolder(binder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }
}

class MyViewHolder(private val binder : ListItemBinding) : RecyclerView.ViewHolder(binder.root){
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
        binder.nameTextView.text = subscriber.name
        binder.emailTextView.text = subscriber.email
        binder.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}