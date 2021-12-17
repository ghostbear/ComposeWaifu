package me.ghostbear.composewaifu.ui.favorites

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ghostbear.composewaifu.local.MainDatabase
import me.ghostbear.composewaifu.local.dao.FavoriteDao
import me.ghostbear.composewaifu.local.model.Waifu

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    database: MainDatabase
) : ViewModel() {

    private val favoriteDao: FavoriteDao = database.favoriteDao()

    var favorites = mutableStateListOf<Waifu>()

    fun loadFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.getAll()
                .collectLatest {
                    favorites.clear()
                    favorites.addAll(it)
                }
        }
    }

    fun removeFromFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            favoriteDao.delete(waifu)
        }
    }
}