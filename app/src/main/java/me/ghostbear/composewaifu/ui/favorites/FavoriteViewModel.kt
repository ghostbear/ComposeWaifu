package me.ghostbear.composewaifu.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ghostbear.composewaifu.domain.interactor.GetWaifuFavorites
import me.ghostbear.composewaifu.domain.interactor.RemoveWaifuFavorite
import me.ghostbear.composewaifu.domain.model.Waifu

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    getWaifuFavorites: GetWaifuFavorites,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : ViewModel() {

    val favorites = getWaifuFavorites.subscribe()

    fun removeFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            removeWaifuFavorite.await(waifu)
        }
    }
}