package me.ghostbear.composewaifu.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ghostbear.composewaifu.local.dao.FavoriteDao
import me.ghostbear.composewaifu.local.model.Waifu

@Database(entities = [Waifu::class], version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

}