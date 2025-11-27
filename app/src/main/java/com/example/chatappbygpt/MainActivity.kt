package com.example.chatappbygpt

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerChats: RecyclerView

    private val chats = listOf(
        Chat(1, "学习资料分享群", R.mipmap.ic_launcher_round),
        Chat(2, "技术交流群", R.mipmap.ic_launcher_round)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerChats = findViewById(R.id.recyclerChats)

        recyclerChats.layoutManager = LinearLayoutManager(this)
        recyclerChats.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        loadAndShowList()
    }

    override fun onResume() {
        super.onResume()
        loadAndShowList()
    }

    private fun loadAndShowList() {

        val lastMap = mutableMapOf<Int, String>()

        for (chat in chats) {
            val msgs = StorageHelper.loadMessages(this, chat.id)
            lastMap[chat.id] = if (msgs.isNotEmpty()) msgs.last().text else ""
        }

        recyclerChats.adapter =
            ChatListAdapter(chats, lastMap) { chat ->
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("chatId", chat.id)
                intent.putExtra("chatName", chat.name)
                startActivity(intent)
            }
    }
}
