package com.gangwonhyuil.gangwonhyuil.ui.ai

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gangwonhyuil.gangwonhyuil.data.remote.ai.AiDTO
import com.gangwonhyuil.gangwonhyuil.data.remote.ai.AiService
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AiViewModel: ViewModel() {

    private val _chatList : MutableLiveData<List<AiDTO>> = MutableLiveData()
    val chatList : LiveData<List<AiDTO>> = _chatList

    val ai = AiService()

    suspend fun sendMessage(message: String) {
        addChat(message, false)
        addChat(ai.sendMessage(message), true)
    }

    private fun addChat(message: String, isAi: Boolean) {
        _chatList.value =
            _chatList.value.orEmpty().plus(AiDTO(message, setTime(), isAi))
    }

    private fun setTime(): String {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH시 mm분"))

    }

}