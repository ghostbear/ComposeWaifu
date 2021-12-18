package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.local.WaifuImageSaver

class SaveWaifuPicture @Inject constructor(
    private val imageSaver: WaifuImageSaver
) {

    suspend fun await(waifu: Waifu) {
        imageSaver.save(waifu)
    }

}