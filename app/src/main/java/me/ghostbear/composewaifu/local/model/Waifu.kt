package me.ghostbear.composewaifu.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("url"), unique = true)])
data class Waifu(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val url: String
)
