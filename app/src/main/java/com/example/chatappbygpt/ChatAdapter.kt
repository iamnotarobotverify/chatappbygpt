package com.example.chatappbygpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappbygpt.R

class ChatAdapter(private val msgList: List<Message>) :
    RecyclerView.Adapter<ChatAdapter.MsgViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgViewHolder {
        val layout = if (viewType == 1)
            R.layout.item_message_right
        else
            R.layout.item_message_left
        // 实现左右布局，通过RecyclerView传递参数

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MsgViewHolder(view)
    }


    override fun getItemViewType(position: Int): Int {
        return if (msgList[position].isUserA) 1 else 0
        // 判断左右布局
    }

    override fun onBindViewHolder(holder: MsgViewHolder, position: Int) {
        val msg = msgList[position]
        holder.msgText.text = msg.text
        holder.msgTime.text = msg.time
    }


    override fun getItemCount(): Int = msgList.size

    class MsgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val msgText: TextView = itemView.findViewById(R.id.msgText)
        val msgTime: TextView = itemView.findViewById(R.id.msgTime)
    }

}
