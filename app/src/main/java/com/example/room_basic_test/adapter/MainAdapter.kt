package com.example.room_basic_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room_basic_test.R
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.databinding.ListItemBinding

class MainAdapter(private val itemClickListener:(Subscriber) -> Unit) : RecyclerView.Adapter<MyViewHolder>(){
    private val subscriberList = ArrayList<Subscriber>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding : ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(subscriberList[position], itemClickListener)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }

    fun setList(subscribers : List<Subscriber>){
        subscriberList.clear()
        subscriberList.addAll(subscribers)
    }

}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(subscriber: Subscriber, itemClickListener: (Subscriber) -> Unit){
        binding.nameTextView.text = subscriber.name
        binding.emailTextView.text = subscriber.email
        binding.listItemLayout.setOnClickListener {
            itemClickListener(subscriber)
        }
    }
}