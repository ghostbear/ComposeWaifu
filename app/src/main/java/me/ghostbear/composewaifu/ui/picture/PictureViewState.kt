package me.ghostbear.composewaifu.ui.picture

import me.ghostbear.domain.waifu.model.Waifu


data class PictureViewState(
    val waifu: Waifu?,
    val filename: String?,
    val error: Exception?
) {
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