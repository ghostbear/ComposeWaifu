package me.ghostbear.composewaifu.ui.gallery

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import me.ghostbear.composewaifu.domain.interactor.AddWaifuFavorite
import me.ghostbear.composewaifu.domain.interactor.GetWaifuCollection
import me.ghostbear.composewaifu.domain.interactor.RemoveWaifuFavorite
import me.ghostbear.composewaifu.domain.model.Waifu
import me.ghostbear.composewaifu.remote.model.WaifuCategory
import me.ghostbear.composewaifu.remote.model.WaifuType
import me.ghostbear.composewaifu.ui.BaseViewModel

@HiltViewModel
class GalleryViewModel @Inject constructor(
    galleryViewState: GalleryViewState,
    private val getWaifuCollection: GetWaifuCollection,
    private val addWaifuFavorite: AddWaifuFavorite,
    private val removeWaifuFavorite: RemoveWaifuFavorite
) : BaseViewModel<GalleryViewState>(galleryViewState) {

    fun getWaifus() {
        setState { state ->
            when (val result = getWaifuCollection.await(state.type, state.category)) {
                is GetWaifuCollection.Result.Success -> {
                    state.copy(waifus = result.data, favorites = listOf(), error = null)
                }
                is GetWaifuCollection.Result.Error -> {
                    state.copy(waifus = listOf(), favorites = listOf(), error = result.error)
                }
            }
        }
    }

    fun addFavorite(waifu: Waifu) {
        setState { state ->
            addWaifuFavorite.await(waifu)
            state.copy(favorites = state.favorites + waifu)
        }
    }

    fun removeFavorite(waifu: Waifu) {
        setState { state ->
            removeWaifuFavorite.await(waifu)
            state.copy(favorites = state.favorites - waifu)
        }
    }

    fun setType(type: WaifuType) {
        setState { state ->
            state.copy(type = type, category = type.categories.first())
        }
        getWaifus()
    }

    fun setCategory(category: WaifuCategory) {
        setState { state ->
            state.copy(category = category)
        }
        getWaifus()
    }

}

