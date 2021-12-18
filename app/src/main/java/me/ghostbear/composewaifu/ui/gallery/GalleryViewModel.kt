package me.ghostbear.composewaifu.ui.gallery

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.ghostbear.composewaifu.domain.interactor.AddWaifuFavorite
import me.ghostbear.composewaifu.domain.interactor.GetWaifuCollection
import me.ghostbear.composewaifu.domain.interactor.RemoveWaifuFavorite
import me.ghostbear.composewaifu.domain.interactor.UpdateWaifuPreferences
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getWaifuCollection: GetWaifuCollection,
    private val updateWaifuPreferences: UpdateWaifuPreferences,
    private val addWaifuFavorite: AddWaifuFavorite,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : ViewModel() {

    var selectedType: WaifuType by mutableStateOf(WaifuType.SFW)
    var selectedCategory: WaifuCategory by mutableStateOf(WaifuCategory.WAIFU)
    // TODO(ghostbear): Fix the flow from the variable it isn't updating when insert/update entries
    val collection = mutableStateListOf<Waifu>()
    val favorites = mutableStateListOf<String>()

    fun init() {
        updateWaifuPreferences()
    }

    fun updateWaifuPreferences() {
        viewModelScope.launch(Dispatchers.IO) {
            updateWaifuPreferences.subscribe(selectedType, selectedCategory)
                .collect {
                    collection.clear()
                    collection.addAll(it)
                }
        }
    }

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
