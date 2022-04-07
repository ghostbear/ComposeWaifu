package me.ghostbear.data.waifu.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import me.ghostbear.data.waifu.local.WaifuDatabase
import me.ghostbear.data.waifu.local.dao.WaifuDao
import me.ghostbear.data.waifu.remote.WaifuApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.ghostbear.data.waifu.local.WaifuImageSaver
import me.ghostbear.data.waifu.mapper.toLocal
import me.ghostbear.data.waifu.mapper.toDomain
import me.ghostbear.data.waifu.mapper.toRemote
import me.ghostbear.domain.waifu.interactor.SaveWaifuPicture
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType
import me.ghostbear.domain.waifu.repository.WaifuRepository

class WaifuRepositoryImpl @Inject constructor(
    private val api: WaifuApi,
    private val saver: WaifuImageSaver,
    database: WaifuDatabase
) : WaifuRepository {

    private val dao: WaifuDao = database.waifuDao()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override suspend fun getLatest(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>> {
        val images = api.getImages(type.toRemote(), category.toRemote())

        scope.launch {
            images
                .toLocal(type.toLocal(), category.toLocal())
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

        return dao.getLatest()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override fun getFavorites(): Flow<List<Waifu>> {
        return dao.getFavorites()
            .map { list ->
                list.map {
                    it.toDomain()
                }
            }
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

    override suspend fun saveImage(waifu: Waifu): SaveWaifuPicture.Result {
        return saver.save(waifu)
    }

}