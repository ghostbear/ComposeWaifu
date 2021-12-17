package me.ghostbear.composewaifu.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WaifuCollection(
    val files: List<String>
)
