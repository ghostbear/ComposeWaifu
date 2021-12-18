package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType

class GetWaifuCollection @Inject constructor(
    private val repository: WaifuRepository
) {

    fun subscribe(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>> {
        return repository.getCollection(type, category)
    }

}