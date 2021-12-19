package me.ghostbear.composewaifu.data.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.ghostbear.composewaifu.data.mapper.toData
import me.ghostbear.composewaifu.data.mapper.toDomain
import me.ghostbear.composewaifu.data.mapper.toLocal
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository
import me.ghostbear.composewaifu.local.WaifuDatabase
import me.ghostbear.composewaifu.local.dao.WaifuDao
import me.ghostbear.composewaifu.remote.WaifuApi
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType
import me.ghostbear.composewaifu.local.model.Waifu as LocalWaifu

class WaifuRepositoryImpl @Inject constructor(
    private val api: WaifuApi,
    database: WaifuDatabase
) : WaifuRepository {

    private val dao: WaifuDao = database.waifuDao()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override suspend fun getLatest(type: WaifuType, category: WaifuCategory): List<Waifu> {
        val images = api.getImages(type, category)

        scope.launch {
            images
                .toData()
                .forEach {
                    try {
                        dao.insert(it)
                    } catch (e: Exception) {
                        val waifu = dao
                            .findByUrl(it.url)
                            .copy(updatedAt = it.updatedAt)
                        dao.update(waifu)
                    }
                }
        }

        return images.toDomain()
    }

    override suspend fun getFavorites(): List<Waifu> {
        return dao.getFavorites().map(LocalWaifu::toDomain)
    }

    override suspend fun addFavorite(waifu: Waifu) {
        val waifu = waifu
            .toLocal()
            .copy(isFavorite = true)
        dao.update(waifu)
    }

    override suspend fun removeFavorite(waifu: Waifu) {
        val waifu = dao
            .findByUrl(waifu.url)
            .copy(isFavorite = false)
        dao.update(waifu)
    }

    override suspend fun findByUrl(url: String): Waifu {
        return dao.findByUrl(url).toDomain()
    }

}