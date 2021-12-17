package me.ghostbear.composewaifu.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WaifuCollection(
    val files: List<String>
)
