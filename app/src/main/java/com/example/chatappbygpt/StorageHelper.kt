package com.example.chatappbygpt

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object StorageHelper {
    private const val PREFS = "chatappbygpt_prefs"
    private val gson = Gson()

    private fun prefs(ctx: Context) = ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun saveMessages(ctx: Context, chatId: Int, messages: List<Message>) {
        val json = gson.toJson(messages)
        prefs(ctx).edit().putString("chat_$chatId", json).apply()
    }

    fun loadMessages(ctx: Context, chatId: Int): MutableList<Message> {
        val json = prefs(ctx).getString("chat_$chatId", null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Message>>() {}.type
        return gson.fromJson(json, type) ?: mutableListOf()
    }
}
