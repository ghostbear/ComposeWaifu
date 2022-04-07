package me.ghostbear.composewaifu.ui.favorites

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import me.ghostbear.domain.waifu.interactor.GetWaifuFavorites
import me.ghostbear.domain.waifu.interactor.RemoveWaifuFavorite
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.composewaifu.ui.BaseViewModel

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    favoriteViewState: FavoriteViewState,
    private val getWaifuFavorites: GetWaifuFavorites,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : BaseViewModel<FavoriteViewState>(favoriteViewState) {

    fun getFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
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
    }

    fun removeFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
                removeWaifuFavorite.await(waifu)
                state.copy(favorites = state.favorites?.minus(waifu))
            }
        }
    }
}

