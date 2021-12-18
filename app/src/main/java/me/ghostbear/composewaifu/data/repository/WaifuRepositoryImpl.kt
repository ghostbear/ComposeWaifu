package me.ghostbear.composewaifu.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import me.ghostbear.composewaifu.data.mapper.toDomain
import me.ghostbear.composewaifu.data.mapper.toLocal
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository
import me.ghostbear.composewaifu.local.MainDatabase
import me.ghostbear.composewaifu.local.dao.FavoriteDao
import me.ghostbear.composewaifu.remote.WaifuApi
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType
import me.ghostbear.composewaifu.local.model.Waifu as LocalWaifu

class WaifuRepositoryImpl @Inject constructor(
    private val api: WaifuApi,
    database: MainDatabase
) : WaifuRepository {

    private val dao: FavoriteDao = database.favoriteDao()

    override fun getCollection(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>> {
        return channelFlow {
            val urls = dao.getAll()
                .map { list ->
                    list.map(LocalWaifu::url)
                }
                .first()

            send(api.getImages(type, category, urls).toDomain())
        }
    }

    override fun getFavorites(): Flow<List<Waifu>> {
        return dao.getAll().map { list ->
            list.map(LocalWaifu::toDomain)
        }
    }

    override suspend fun addFavorite(waifu: Waifu) {
        dao.insert(waifu.toLocal())
    }

    override suspend fun removeFavorite(waifu: Waifu) {
        val waifu = dao.findByUrl(waifu.url)
        dao.delete(waifu)
    }

}