package me.ghostbear.domain.waifu.model

import java.util.*

data class Waifu(
    val url: String,
    val isFavorite: Boolean = false,
    val updatedAt: Date = Date(),
    val type: WaifuType,
    val category: WaifuCategory,
)
