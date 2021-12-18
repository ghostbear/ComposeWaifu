package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository

class GetWaifuFavorites @Inject constructor(
    private val repository: WaifuRepository
) {

    fun subscribe(): Flow<List<Waifu>> {
        return repository.getFavorites()
    }

}