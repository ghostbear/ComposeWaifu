package me.ghostbear.domain.waifu.interactor

import javax.inject.Inject
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType
import me.ghostbear.domain.waifu.repository.WaifuRepository

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