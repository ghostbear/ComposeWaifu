package me.ghostbear.domain.waifu.interactor

import javax.inject.Inject
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.repository.WaifuRepository

class SaveWaifuPicture @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun await(waifu: Waifu): Result {
        return try {
            return repository.saveImage(waifu)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    sealed class Result  {
        data class Error(val message: Exception) : Result()
        data class Success(val filename: String) : Result()
    }
}