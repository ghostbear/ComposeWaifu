package me.ghostbear.data.waifu.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import me.ghostbear.data.waifu.local.model.Waifu

@Dao
interface WaifuDao {

    @Query("SELECT * FROM waifu ORDER BY updatedAt DESC LIMIT 30")
    fun getLatest(): Flow<List<Waifu>>

    @Query("SELECT * FROM waifu WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<Waifu>>

    @Query("SELECT * FROM waifu WHERE url = :url")
    fun findByUrl(url: String): Waifu

    @Query("UPDATE waifu SET updatedAt = strftime('%s', 'now') WHERE url in (:urls)")
    fun updateUpdatedAtByUrl(vararg urls: String)

    @Transaction
    fun updateUpdatedAtByUrl(waifu: List<Waifu>) {
        updateUpdatedAtByUrl(*waifu.map { it.url }.toTypedArray())
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg waifu: Waifu): List<Long>

    @Transaction
    fun upsert(waifu: List<Waifu>) {
        val inserted = insert(*waifu.toTypedArray())
        val updateList = inserted.mapIndexedNotNull { index, result ->
            if (result == -1L) waifu[index] else null
        }
        updateUpdatedAtByUrl(updateList)
    }

    @Query("UPDATE waifu SET isFavorite = 1 WHERE url = :url")
    fun favorite(url: String)

    @Transaction
    fun favorite(waifu: Waifu) {
        favorite(waifu.url)
    }

    @Query("UPDATE waifu SET isFavorite = 0 WHERE url = :url")
    fun unfavorite(url: String)

    @Transaction
    fun unfavorite(waifu: Waifu) {
        unfavorite(waifu.url)
    }
}