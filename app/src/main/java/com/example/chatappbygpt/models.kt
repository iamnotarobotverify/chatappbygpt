package com.example.chatappbygpt

data class Chat(
    val id: Int,
    val name: String,
    val avatarRes: Int
)

data class Message(
    val isUserA: Boolean,   // ← 这个字段最重要！
    val text: String,
    val time: String
)
