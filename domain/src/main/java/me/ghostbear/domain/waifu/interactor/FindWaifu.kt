package me.ghostbear.domain.waifu.interactor

import javax.inject.Inject
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.repository.WaifuRepository

class FindWaifu @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun await(url: String): Result {
        return try {
            Result.Success(repository.findByUrl(url))
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    sealed class Result {
        data class Success(val waifu: Waifu) : Result()
        data class Error(val error: Exception) : Result()
    }

}