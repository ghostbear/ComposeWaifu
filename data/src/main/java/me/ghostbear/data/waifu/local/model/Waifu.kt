package me.ghostbear.data.waifu.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Waifu(
    @PrimaryKey val url: String,
    val isFavorite: Boolean = false,
    val updatedAt: Date = Date()
)
