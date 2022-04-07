package me.ghostbear.domain.waifu.interactor

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType
import me.ghostbear.domain.waifu.repository.WaifuRepository

class GetWaifuCollection @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun subscribe(type: WaifuType, category: WaifuCategory): Flow<List<Waifu>> {
        return repository.getLatest(type, category)
    }

}