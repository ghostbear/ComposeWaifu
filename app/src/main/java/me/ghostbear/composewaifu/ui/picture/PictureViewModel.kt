package me.ghostbear.composewaifu.ui.picture

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import me.ghostbear.composewaifu.domain.interactor.FindWaifu
import me.ghostbear.composewaifu.domain.interactor.SaveWaifuPicture
import me.ghostbear.composewaifu.ui.BaseViewModel

@HiltViewModel
class PictureViewModel @Inject constructor(
    pictureViewState: PictureViewState,
    private val findWaifu: FindWaifu,
    private val saveWaifuPicture: SaveWaifuPicture
) : BaseViewModel<PictureViewState>(pictureViewState) {

    fun getWaifu(url: String) {
        setState { state ->
            when (val result = findWaifu.await(url)) {
                is FindWaifu.Result.Success -> {
                    state.copy(waifu = result.waifu, filename = null, error = null)
                }
                is FindWaifu.Result.Error -> {
                    state.copy(waifu = null, filename = null, error = state.error)
                }
            }
        }
    }

    fun save() {
        setState { state ->
            when (val result = saveWaifuPicture.await(state.waifu!!)) {
                is SaveWaifuPicture.Result.Error -> {
                    state.copy(error = result.message)
                }
                is SaveWaifuPicture.Result.Success -> {
                    state.copy(filename = result.filename)
                }
            }
        }
    }

    fun reset() {
        setState { state ->
            state.copy(filename = null)
        }
    }

}
