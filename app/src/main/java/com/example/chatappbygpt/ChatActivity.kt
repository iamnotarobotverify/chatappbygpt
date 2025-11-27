package com.example.chatappbygpt

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappbygpt.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatActivity : AppCompatActivity() {

    private lateinit var recyclerMsgs: RecyclerView
    private lateinit var editMsg: EditText
    private lateinit var btnSend: Button

    private var chatId: Int = 0
    private var chatName: String = ""
    private var isUserA = true

    private lateinit var adapter: ChatAdapter
    private val msgList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chatId = intent.getIntExtra("chatId", 0)
        chatName = intent.getStringExtra("chatName") ?: ""

        title = chatName

        // 绑定控件
        recyclerMsgs = findViewById(R.id.recyclerMsgs)
        editMsg = findViewById(R.id.editMsg)
        btnSend = findViewById(R.id.btnSend)

        // 列表设置
        recyclerMsgs.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(msgList)
        recyclerMsgs.adapter = adapter

        // 加载历史聊天记录
        msgList.addAll(StorageHelper.loadMessages(this, chatId))
        adapter.notifyDataSetChanged()

        // 发送按钮
        btnSend.setOnClickListener {
            val text = editMsg.text.toString()
            if (text.isNotEmpty()) {
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                val msg = Message(isUserA, text,  currentTime)

                msgList.add(msg)
                StorageHelper.saveMessages(this, chatId, msgList)

                adapter.notifyItemInserted(msgList.size - 1)
                recyclerMsgs.scrollToPosition(msgList.size - 1)
                editMsg.setText("")
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_chat, menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.menu_switch -> {
                isUserA = !isUserA
                val role = if (isUserA) "自己" else "对方"
                AlertDialog.Builder(this)
                    .setMessage("你现在扮演：$role")
                    .setPositiveButton("好的", null)
                    .show()
            }

            R.id.menu_clear -> {
                AlertDialog.Builder(this)
                    .setTitle("清空记录？")
                    .setMessage("确定删除聊天记录吗？")
                    .setPositiveButton("确定") { _, _ ->
                        msgList.clear()
                        StorageHelper.saveMessages(this, chatId, msgList)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("取消", null)
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
