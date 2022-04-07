package me.ghostbear.domain.waifu.interactor

import javax.inject.Inject
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.repository.WaifuRepository

class GetWaifuFavorites @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun await(): Result {
        return try {
            Result.Success(repository.getFavorites())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    sealed class Result {
        data class Success(val favorites: List<Waifu>) : Result()
        data class Error(val error: Exception) : Result()
    }

}