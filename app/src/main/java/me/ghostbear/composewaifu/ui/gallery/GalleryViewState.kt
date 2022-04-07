package me.ghostbear.composewaifu.ui.gallery

import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType


data class GalleryViewState(
    val waifus: List<Waifu>?,
    val favorites: List<Waifu>,
    val type: WaifuType,
    val category: WaifuCategory,
    val error: Throwable?
) {

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