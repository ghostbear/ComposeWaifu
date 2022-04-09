package me.ghostbear.data.waifu.repository

import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.ghostbear.data.waifu.local.WaifuDatabase
import me.ghostbear.data.waifu.local.WaifuImageSaver
import me.ghostbear.data.waifu.local.dao.WaifuDao
import me.ghostbear.data.waifu.mapper.toDomain
import me.ghostbear.data.waifu.mapper.toLocal
import me.ghostbear.data.waifu.mapper.toRemote
import me.ghostbear.data.waifu.remote.WaifuApi
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

    private var job: Job? = null

    override suspend fun getLatest(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>> {
        job?.cancel()
        job = scope.launch {
            val images = api.getImages(type.toRemote(), category.toRemote())
                .toLocal(type.toLocal(), category.toLocal())
            dao.upsert(images)
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
        val localWaifu = waifu.toLocal()
        dao.favorite(localWaifu)
    }

    override suspend fun removeFavorite(waifu: Waifu) {
        val localWaifu = waifu.toLocal()
        dao.unfavorite(localWaifu)
    }

    override suspend fun findByUrl(url: String): Waifu {
        return dao.findByUrl(url)
            .toDomain()
    }

    override suspend fun saveImage(waifu: Waifu): SaveWaifuPicture.Result {
        return saver.save(waifu)
    }

}