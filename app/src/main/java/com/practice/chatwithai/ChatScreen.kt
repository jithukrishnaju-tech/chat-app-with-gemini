package com.practice.chatwithai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.ai.client.generativeai.Chat
import com.practice.chatwithai.model.MessageModel

@Composable
fun ChatScreen(viewModel: ChatBotViewModel) {
    Column {
        AppHeader()
        MessageList(modifier = Modifier.weight(1f), viewModel.messageList)
        MessageInput(onSend = { it ->
            viewModel.addData(it)
        })
    }
}

@Composable
fun AppHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
    ) {
        Text(text = "I am Grook", modifier = Modifier.padding(20.dp), fontSize = 30.sp)
    }
}

@Composable
fun MessageInput(modifier: Modifier = Modifier, onSend: (String) -> Unit) {
    var message by remember {
        mutableStateOf("")
    }
    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(value = message, onValueChange = { it ->
            message = it
        }, Modifier.weight(1f))
        IconButton(onClick = {
            onSend(message)
            message = ""
        }) { Icon(imageVector = Icons.Default.Send, contentDescription = "Send icon") }

    }
}

@Composable
fun MessageList(modifier: Modifier, itemsList: List<MessageModel>) {
    LazyColumn(modifier = modifier, reverseLayout = true) {
        items(itemsList.reversed()) {
            ChatItems(it)
        }
    }
}

@Composable
fun ChatItems(message: MessageModel) {
    val isModel: Boolean = message.role == "model"
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .align(if (isModel) Alignment.BottomStart else Alignment.BottomEnd)
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp
                )
                .clip(RoundedCornerShape(48f))
                .background(if (isModel) Color.Blue else Color.Green)
                .padding(16.dp)
        ) {
            Text(
                text = message.message,
                fontWeight = FontWeight.W500,
                color = Color.White
            )
        }
    }
}