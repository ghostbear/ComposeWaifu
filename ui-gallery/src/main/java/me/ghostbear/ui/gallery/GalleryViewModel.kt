package me.ghostbear.ui.gallery

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import me.ghostbear.domain.waifu.interactor.AddWaifuFavorite
import me.ghostbear.domain.waifu.interactor.GetWaifuCollection
import me.ghostbear.domain.waifu.interactor.RemoveWaifuFavorite
import me.ghostbear.domain.waifu.model.Waifu

import me.ghostbear.base.BaseViewModel
import me.ghostbear.domain.waifu.model.WaifuCategory
import me.ghostbear.domain.waifu.model.WaifuType

@HiltViewModel
class GalleryViewModel @Inject constructor(
    galleryViewState: GalleryViewState,
    private val getWaifuCollection: GetWaifuCollection,
    private val addWaifuFavorite: AddWaifuFavorite,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : BaseViewModel<GalleryViewState>(galleryViewState) {

    private var currentType: MutableStateFlow<WaifuType?> = MutableStateFlow(null)
    private var currentCategory: MutableStateFlow<WaifuCategory?> = MutableStateFlow(null)

    init {
        _state
            .onEach { state ->
                currentType.emit(state.type)
                currentCategory.emit(state.category)
            }
            .launchIn(viewModelScope)

        viewModelScope.launch(Dispatchers.IO) {
            combine(currentType, currentCategory) { type, category ->
                type to category
            }
                .collectLatest { (type, category) ->
                    if (type == null || category == null) return@collectLatest
                    getWaifuCollection.subscribe(type, category)
                        .drop(1)
                        .catch { error ->
                            _state.update { state ->
                                state.copy(waifus = null, error = error)
                            }
                        }
                        .collectLatest {
                            _state.update { state ->
                                state.copy(waifus = it, error = null)
                            }
                        }
                }
        }
    }

    fun toggleFavorite(waifu: Waifu) {
        viewModelScope.launch(Dispatchers.IO) {
            if (waifu.isFavorite) {
                removeWaifuFavorite.await(waifu)
            } else {
                addWaifuFavorite.await(waifu)
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

