package com.gangwonhyuil.gangwonhyuil.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _exceptions: MutableSharedFlow<Exception> = MutableSharedFlow()

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                throwable.let {
                    _exceptions.emit(Exception(it))
                }
            }
        }

    protected val viewModelScopeEH = viewModelScope + exceptionHandler
}