package me.ghostbear.composewaifu.ui.gallery

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import me.ghostbear.domain.waifu.interactor.AddWaifuFavorite
import me.ghostbear.domain.waifu.interactor.GetWaifuCollection
import me.ghostbear.domain.waifu.interactor.RemoveWaifuFavorite
import me.ghostbear.domain.waifu.model.Waifu

import me.ghostbear.composewaifu.ui.BaseViewModel
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType

@HiltViewModel
class GalleryViewModel @Inject constructor(
    galleryViewState: GalleryViewState,
    private val getWaifuCollection: GetWaifuCollection,
    private val addWaifuFavorite: AddWaifuFavorite,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : BaseViewModel<GalleryViewState>(galleryViewState) {

    private var currentJob: Job? = null

    private var currentType: WaifuType? = null
    private var currentCategory: WaifuCategory? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _state.collectLatest { state ->
                var refresh = false
                if (state.type != currentType) {
                    currentType = state.type
                    refresh = true
                }

                if (state.category != currentCategory) {
                    currentCategory = state.category
                    refresh = true
                }

                if (refresh) {
                    fetchWaifuList()
                }
            }
        }

    }

    fun fetchWaifuList() {
        currentJob?.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
            val state = _state.value
            val result = getWaifuCollection.await(state.type, state.category)
            _state.update { state ->
                when (result) {
                    is GetWaifuCollection.Result.Success -> {
                        state.copy(waifus = result.data, favorites = listOf(), error = null)
                    }
                    is GetWaifuCollection.Result.Error -> {
                        state.copy(waifus = listOf(), favorites = listOf(), error = result.error)
                    }
                }
            }
        }
    }

    fun addFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
                addWaifuFavorite.await(waifu)
                state.copy(favorites = state.favorites + waifu)
            }
        }
    }

    fun removeFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
                removeWaifuFavorite.await(waifu)
                state.copy(favorites = state.favorites - waifu)
            }
        }
    }

    fun setType(type: WaifuType) {
        _state.update { state ->
            state.copy(type = type, category = type.categories.first())
        }
    }

    fun setCategory(category: WaifuCategory) {
        _state.update { state ->
            state.copy(category = category)
        }
    }

}

