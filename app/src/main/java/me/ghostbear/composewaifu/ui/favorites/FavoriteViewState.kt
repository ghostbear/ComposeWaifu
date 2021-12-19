package me.ghostbear.composewaifu.ui.favorites

import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.ui.ViewState

data class FavoriteViewState(
    val favorites: List<Waifu>?,
    val error: Exception?
) : ViewState() {

    val hasError: Boolean
        get() {
            return error != null
        }

    val isLoading: Boolean
        get() {
            return favorites == null && error == null
        }

    companion object {
        val Empty = FavoriteViewState(
            null,
            null
        )
    }
}