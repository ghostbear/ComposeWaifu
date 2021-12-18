package me.ghostbear.composewaifu.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import me.ghostbear.composewaifu.local.model.Waifu

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM waifu")
    fun getAll(): Flow<List<Waifu>>

    @Query("SELECT * FROM waifu WHERE url = :url")
    fun findByUrl(url: String): Waifu

    @Insert
    fun insert(waifu: Waifu)

    @Delete
    fun delete(waifu: Waifu)
}