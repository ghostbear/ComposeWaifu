package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.local.WaifuImageSaver

class SaveWaifuPicture @Inject constructor(
    private val imageSaver: WaifuImageSaver
) {

    suspend fun await(waifu: Waifu): Result {
        return try {
            when (val result = imageSaver.save(waifu)) {
                is WaifuImageSaver.Result.Error -> Result.Error(result.message)
                is WaifuImageSaver.Result.Success -> Result.Success(result.filename)
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    sealed class Result  {
        data class Error(val message: Exception) : Result()
        data class Success(val filename: String) : Result()
    }
}