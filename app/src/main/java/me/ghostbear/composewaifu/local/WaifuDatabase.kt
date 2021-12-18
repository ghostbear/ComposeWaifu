package me.ghostbear.composewaifu.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.ghostbear.composewaifu.local.converter.DateConverter
import me.ghostbear.composewaifu.local.dao.WaifuDao
import me.ghostbear.composewaifu.local.model.Waifu

@Database(entities = [Waifu::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class WaifuDatabase : RoomDatabase() {

    abstract fun waifuDao(): WaifuDao

}