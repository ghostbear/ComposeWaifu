package me.ghostbear.composewaifu.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Waifu(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String
)
