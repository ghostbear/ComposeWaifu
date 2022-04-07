package me.ghostbear.domain.waifu.repository

import kotlinx.coroutines.flow.Flow
import me.ghostbear.domain.waifu.interactor.SaveWaifuPicture
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType

interface WaifuRepository {

    suspend fun getLatest(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>>

    fun getFavorites(): Flow<List<Waifu>>

    suspend fun addFavorite(waifu: Waifu)

    suspend fun removeFavorite(waifu: Waifu)

    suspend fun findByUrl(url: String): Waifu

    suspend fun saveImage(waifu: Waifu) : SaveWaifuPicture.Result
}