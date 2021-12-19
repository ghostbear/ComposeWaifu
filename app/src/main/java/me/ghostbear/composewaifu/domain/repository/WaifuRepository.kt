package me.ghostbear.composewaifu.domain.repository

import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType

interface WaifuRepository {

    suspend fun getLatest(type: WaifuType, category: WaifuCategory): List<Waifu>

    suspend fun getFavorites(): List<Waifu>

    suspend fun addFavorite(waifu: Waifu)

    suspend fun removeFavorite(waifu: Waifu)

    suspend fun findByUrl(url: String): Waifu

}