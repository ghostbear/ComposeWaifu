package me.ghostbear.data.waifu.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class WaifuCollection(
    val files: List<String>
)
