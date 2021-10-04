package com.example.room_basic_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.room_basic_test.R
import com.example.room_basic_test.database.Subscriber
import com.example.room_basic_test.databinding.ItemListBinding

// 매개 변수로 subscber 클래스 변수와 함수 하나를 받는다
class MainRecyclerAdapter(private val subscriberList : List<Subscriber>,
    private val clickListener : (Subscriber) -> Unit) : RecyclerView.Adapter<MainViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binder = DataBindingUtil.inflate<ItemListBinding>(layoutInflater, R.layout.item_list, parent, false)
        return MainViewHolder(binder)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(subscriberList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return subscriberList.size
    }

}
class MainViewHolder(private val binder : ItemListBinding) : RecyclerView.ViewHolder(binder.root){
    fun bind(subscriber: Subscriber, clickListener: (Subscriber) -> Unit){
        binder.nameTextView.text = subscriber.name
        binder.emailTextView.text = subscriber.email
        // 항목을 클릭하면 매개변수로 받은 메서드가 발동되게 한다
        binder.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }
}

