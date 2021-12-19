package me.ghostbear.composewaifu.domain.interactor

import javax.inject.Inject
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.domain.repository.WaifuRepository
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType

class GetWaifuCollection @Inject constructor(
    private val repository: WaifuRepository
) {

    suspend fun await(type: WaifuType, category: WaifuCategory): Result {
        return try {
            Result.Success(repository.getLatest(type, category))
        } catch (e: Exception) {
            Result.Error(e)
        }

    }

    sealed class Result {
        data class Success(val data: List<Waifu>) : Result()
        data class Error(val error: Exception) : Result()
    }

}