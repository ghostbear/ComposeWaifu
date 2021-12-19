package me.ghostbear.composewaifu.ui.gallery

import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType
import me.ghostbear.composewaifu.ui.ViewState

data class GalleryViewState(
    val waifus: List<Waifu>?,
    val favorites: List<Waifu>,
    val type: WaifuType,
    val category: WaifuCategory,
    val error: Exception?
) : ViewState() {

    val hasError: Boolean
        get() {
            return error != null
        }

    val isLoading: Boolean
        get() {
            return waifus == null && error == null
        }

    companion object {
        val Empty = GalleryViewState(
            null,
            listOf(),
            WaifuType.SFW,
            WaifuCategory.WAIFU,
            null
        )
    }

}