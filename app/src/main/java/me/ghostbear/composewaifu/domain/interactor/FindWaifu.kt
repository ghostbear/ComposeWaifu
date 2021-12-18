package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository

class FindWaifu @Inject constructor(
    private val repository: WaifuRepository
) {

    fun await(url: String): Waifu {
        return repository.findByUrl(url)
    }

}