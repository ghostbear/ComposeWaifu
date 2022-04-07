package me.ghostbear.domain.waifu.interactor

import javax.inject.Inject
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.repository.WaifuRepository

class AddWaifuFavorite @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun await(waifu: Waifu) {
        repository.addFavorite(waifu)
    }

}