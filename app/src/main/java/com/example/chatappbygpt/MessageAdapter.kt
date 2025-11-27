package com.example.chatappbygpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MessageAdapter(
    private val chats: List<Chat>,
    private val lastMessages: Map<Int, String>,
    private val onClick: (Chat) -> Unit
) : RecyclerView.Adapter<MessageAdapter.VH>() {

    inner class VH(view: View): RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgAvatar)
        val name: TextView = view.findViewById(R.id.tvName)
        val last: TextView = view.findViewById(R.id.tvLast)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val chat = chats[position]
        holder.img.setImageResource(chat.avatarRes)
        holder.name.text = chat.name
        holder.last.text = lastMessages[chat.id] ?: ""
        holder.itemView.setOnClickListener { onClick(chat) }
    }

    override fun getItemCount() = chats.size
}
