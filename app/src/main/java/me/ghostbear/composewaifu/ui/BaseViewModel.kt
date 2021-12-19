package me.ghostbear.composewaifu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel<T : ViewState>(value: T) : ViewModel() {

    private var mutableState = MutableStateFlow(value)
    val state: StateFlow<T> get() = mutableState

    fun setState(block: suspend (T) -> T) {
        viewModelScope.launch(Dispatchers.IO) {
            mutableState.value = block(mutableState.value)
        }
    }

    fun getState(block: suspend (T) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            block(mutableState.value)
        }
    }

}

open class ViewState