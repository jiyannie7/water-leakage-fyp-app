package com.example.waterleakageapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waterleakageapp.R
import com.example.waterleakageapp.data.Message
import com.example.waterleakageapp.utils.Constants.RECEIVE_ID
import com.example.waterleakageapp.utils.Constants.SEND_ID

class MessagingAdapter :
    RecyclerView.Adapter<MessagingAdapter.ViewHolder>() {

    var messagesList = mutableListOf<Message>()
    inner class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var txtMessage : TextView = itemView.findViewById(R.id.tv_message)
        var txtBotMessage : TextView = itemView.findViewById(R.id.tv_bot_message)
        init {
            itemView.setOnClickListener {
                messagesList.removeAt((adapterPosition))
                notifyItemRemoved(adapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.item_message,parent,false)))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val currentMessage = messagesList[position]

        when(currentMessage.id){
            SEND_ID -> {
                holder.txtMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.txtBotMessage.visibility = View.GONE
            }
            RECEIVE_ID ->{
                holder.txtBotMessage.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.txtMessage.visibility = View.GONE
            }
        }//WHEN
    }

    override fun getItemCount(): Int {
       return messagesList.size
    }
    fun insertMessage(message: Message){
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }
}