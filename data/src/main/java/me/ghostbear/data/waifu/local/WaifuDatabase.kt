package me.ghostbear.data.waifu.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import me.ghostbear.data.waifu.local.converter.DateConverter
import me.ghostbear.data.waifu.local.dao.WaifuDao
import me.ghostbear.data.waifu.local.model.Waifu

@Database(entities = [Waifu::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class WaifuDatabase : RoomDatabase() {

    abstract fun waifuDao(): WaifuDao

}