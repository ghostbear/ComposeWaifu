package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository

class AddWaifuFavorite @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun await(waifu: Waifu) {
        repository.addFavorite(waifu)
    }

}