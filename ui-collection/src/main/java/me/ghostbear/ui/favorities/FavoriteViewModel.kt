package me.ghostbear.ui.favorities

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import me.ghostbear.domain.waifu.interactor.GetWaifuFavorites
import me.ghostbear.domain.waifu.interactor.RemoveWaifuFavorite
import me.ghostbear.domain.waifu.model.Waifu
import me.ghostbear.base.BaseViewModel

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    favoriteViewState: FavoriteViewState,
    private val getWaifuFavorites: GetWaifuFavorites,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : BaseViewModel<FavoriteViewState>(favoriteViewState) {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getWaifuFavorites.subscribe()
                .catch { error ->
                    _state.update { state ->
                        state.copy(favorites = listOf(), error = error)
                    }
                }
                .collectLatest {
                    _state.update { state ->
                        Log.d("FavoriteViewModel", "Here have some data")
                        state.copy(favorites = it, error = null)
                    }
                }
        }
    }

    fun removeFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            removeWaifuFavorite.await(waifu)
        }
    }
}

