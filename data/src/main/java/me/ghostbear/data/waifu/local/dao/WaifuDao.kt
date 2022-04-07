package me.ghostbear.data.waifu.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import me.ghostbear.data.waifu.local.model.Waifu

@Dao
interface WaifuDao {

    @Query("SELECT * FROM waifu ORDER BY updatedAt DESC LIMIT 30")
    fun getLatest(): LiveData<List<Waifu>>

    @Query("SELECT * FROM waifu WHERE isFavorite = 1")
    suspend fun getFavorites(): List<Waifu>

    @Query("SELECT * FROM waifu WHERE url = :url")
    fun findByUrl(url: String): Waifu

    @Insert
    fun insert(waifu: Waifu)

    @Update
    fun update(waifu: Waifu)

    @Delete
    fun delete(waifu: Waifu)
}