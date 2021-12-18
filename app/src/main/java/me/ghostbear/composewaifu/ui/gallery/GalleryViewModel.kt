package me.ghostbear.composewaifu.ui.gallery

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.ghostbear.composewaifu.domain.interactor.AddWaifuFavorite
import me.ghostbear.composewaifu.domain.interactor.GetWaifuCollection
import me.ghostbear.composewaifu.domain.interactor.GetWaifuFavorites
import me.ghostbear.composewaifu.domain.interactor.RemoveWaifuFavorite
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType

@HiltViewModel
class GalleryViewModel @Inject constructor(
    getWaifuCollection: GetWaifuCollection,
    private val addWaifuFavorite: AddWaifuFavorite,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : ViewModel() {

    var selectedType: WaifuType by mutableStateOf(WaifuType.SFW)
    var selectedCategory: WaifuCategory by mutableStateOf(WaifuCategory.WAIFU)
    val collection by derivedStateOf {
        getWaifuCollection.subscribe(selectedType, selectedCategory)
    }
    val favorites = mutableStateListOf<String>()


    fun addFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            addWaifuFavorite.await(waifu)
            favorites.add(waifu.url)
        }
    }

    fun removeFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            removeWaifuFavorite.await(waifu)
            favorites.remove(waifu.url)
        }
    }

}