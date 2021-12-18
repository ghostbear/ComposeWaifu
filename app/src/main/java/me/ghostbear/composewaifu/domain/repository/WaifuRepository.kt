package me.ghostbear.composewaifu.domain.repository

import kotlinx.coroutines.flow.Flow
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType

interface WaifuRepository {

    fun getCollection(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>>

    fun getFavorites(): Flow<List<Waifu>>

    suspend fun addFavorite(waifu: Waifu)

    suspend fun removeFavorite(waifu: Waifu)

}