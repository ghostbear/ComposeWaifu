package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository

class RemoveWaifuFavorite @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun await(waifu: Waifu) {
        repository.removeFavorite(waifu)
    }

}