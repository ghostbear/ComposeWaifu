package me.ghostbear.composewaifu.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

open class BaseViewModel<T>(value: T) : ViewModel() {

    protected var _state = MutableStateFlow(value)
    val state: StateFlow<T> get() = _state

}
