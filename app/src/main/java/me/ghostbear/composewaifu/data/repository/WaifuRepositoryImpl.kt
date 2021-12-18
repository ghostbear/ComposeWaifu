package me.ghostbear.composewaifu.data.repository

import android.util.Log
import androidx.compose.runtime.snapshotFlow
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.transform
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

    fun Flow<List<LocalWaifu>>.transformDomain(): Flow<List<Waifu>> = transform { value ->
        emit(value.map(LocalWaifu::toDomain))
    }

    override fun getLatest(): Flow<List<Waifu>> {
        return dao.getLatest().transformDomain()
    }

    override fun getFavorites(): Flow<List<Waifu>> {
        return dao.getFavorites().map { list ->
            list.map(LocalWaifu::toDomain)
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

    override fun updateLatest(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>> {
        return flow {
            api.getImages(type, category)
                .toData()
                .forEach {
                    try {
                        dao.insert(it)
                        Log.d("getCollection", "Inserting waifu: $it")
                    } catch (e: Exception) {
                        val waifu = dao
                            .findByUrl(it.url)
                            .copy(updatedAt = it.updatedAt)
                        Log.d("getCollection", "Updating waifu: $waifu")
                        dao.update(waifu)
                    }
                }

            emitAll(dao.getLatest().transformDomain())
        }
    }

    override fun findByUrl(url: String): Waifu {
        return dao.findByUrl(url).toDomain()
    }

}