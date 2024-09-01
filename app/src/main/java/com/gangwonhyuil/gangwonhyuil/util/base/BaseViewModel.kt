package com.gangwonhyuil.gangwonhyuil.util.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel() : ViewModel() {
    private val _exceptions: MutableSharedFlow<Exception> = MutableSharedFlow()
    protected val exceptions = _exceptions.asSharedFlow()

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