package com.practice.chatwithai

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.practice.chatwithai.model.MessageModel
import kotlinx.coroutines.launch

class ChatBotViewModel : ViewModel() {
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }
    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-pro-latest",
        apiKey = Constants.geminiApi
    )

    fun addData(message: String) {
        Log.d("chat", "added data is $message")
        viewModelScope.launch {
            val chat = generativeModel.startChat(history = messageList.map {
                content(role = it.role) {
                    text(it.message)
                }
            }.toList())
            messageList.add(MessageModel(message, "user"))

            val response = chat.sendMessage(message)
            messageList.add(MessageModel(response.text.toString(), "model"))
            Log.d("message", "message = ${response.text}")
        }


    }
}