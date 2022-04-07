package me.ghostbear.domain.waifu.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.repository.WaifuRepository

class GetWaifuFavorites @Inject constructor(
    private val repository: WaifuRepository
) {

    fun subscribe(): Flow<List<Waifu>> {
        return repository.getFavorites()
    }

}