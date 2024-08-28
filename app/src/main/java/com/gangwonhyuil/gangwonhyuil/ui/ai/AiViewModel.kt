package com.gangwonhyuil.gangwonhyuil.ui.ai

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gangwonhyuil.gangwonhyuil.data.remote.Ai.AiDTO

class AiViewModel : ViewModel() {

    private val _rightchatList : MutableLiveData<List<AiDTO>> = MutableLiveData()
    val rightchatList : LiveData<List<AiDTO>> = _rightchatList
    private val _leftchatList : MutableLiveData<List<AiDTO>> = MutableLiveData()
    val leftchatList : LiveData<List<AiDTO>> = _leftchatList

    fun addRightChating(chat: AiDTO) {
        val currentList = _rightchatList.value.orEmpty().toMutableList()
        currentList.add(chat)
        _rightchatList.value = currentList

    }

    fun addLeftChating(chat: AiDTO) {
        val currentList = _leftchatList.value.orEmpty().toMutableList()
        currentList.add(chat)
        _leftchatList.value = currentList

    }

}