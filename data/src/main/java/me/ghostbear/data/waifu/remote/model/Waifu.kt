package me.ghostbear.data.waifu.remote.model

import java.util.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Waifu(
    val url: String,
    @Transient val isFavorite: Boolean = false,
    @Transient val updatedAt: Date = Date(),
    @Transient val type: WaifuType = WaifuType.SFW,
    @Transient val category: WaifuCategory = WaifuCategory.WAIFU
)