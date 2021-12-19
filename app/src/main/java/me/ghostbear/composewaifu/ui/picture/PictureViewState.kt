package me.ghostbear.composewaifu.ui.picture

import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.ui.ViewState

data class PictureViewState(
    val waifu: Waifu?,
    val filename: String?,
    val error: Exception?
) : ViewState() {
    val hasError: Boolean
        get() {
            return error != null
        }

    val isLoading: Boolean
        get() {
            return waifu == null && error == null
        }

    companion object {
        val Empty = PictureViewState(
            null,
            null,
            null
        )
    }
}