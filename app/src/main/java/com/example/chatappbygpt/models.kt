package com.example.chatappbygpt

data class Chat(
    val id: Int,
    val name: String,
    val avatarRes: Int
)

data class Message(
    val isUserA: Boolean, //判断是哪个用户
    val text: String,
    val time: String
)
