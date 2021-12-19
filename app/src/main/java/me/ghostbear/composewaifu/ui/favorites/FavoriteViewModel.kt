package me.ghostbear.composewaifu.ui.favorites

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import me.ghostbear.composewaifu.domain.interactor.GetWaifuFavorites
import me.ghostbear.composewaifu.domain.interactor.RemoveWaifuFavorite
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.ui.BaseViewModel

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    favoriteViewState: FavoriteViewState,
    private val getWaifuFavorites: GetWaifuFavorites,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : BaseViewModel<FavoriteViewState>(favoriteViewState) {

    fun getFavorites() {
        setState { state ->
            when (val result = getWaifuFavorites.await()) {
                is GetWaifuFavorites.Result.Success -> {
                    state.copy(favorites = result.favorites, error = null)
                }
                is GetWaifuFavorites.Result.Error -> {
                    state.copy(favorites = listOf(), error = result.error)
                }
            }
        }
    }

    fun removeFavorite(waifu: Waifu) {
        setState { state ->
            removeWaifuFavorite.await(waifu)
            state.copy(favorites = state.favorites?.minus(waifu))
        }
    }
}

