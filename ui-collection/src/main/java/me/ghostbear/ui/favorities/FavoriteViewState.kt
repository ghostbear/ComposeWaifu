package me.ghostbear.ui.favorities

import me.ghostbear.domain.waifu.model.Waifu

data class FavoriteViewState(
    val favorites: List<Waifu>?,
    val error: Throwable?
) {

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