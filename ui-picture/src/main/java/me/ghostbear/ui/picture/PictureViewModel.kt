package me.ghostbear.ui.picture

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import me.ghostbear.domain.waifu.interactor.FindWaifu
import me.ghostbear.domain.waifu.interactor.SaveWaifuPicture
import me.ghostbear.base.BaseViewModel

@HiltViewModel
class PictureViewModel @Inject constructor(
    pictureViewState: PictureViewState,
    private val findWaifu: FindWaifu,
    private val saveWaifuPicture: SaveWaifuPicture
) : BaseViewModel<PictureViewState>(pictureViewState) {

    fun getWaifu(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
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
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { state ->
                when (val result = saveWaifuPicture.await(state.waifu!!)) {
                    is SaveWaifuPicture.Result.Error -> state.copy(waifu = null, error = result.message)
                    is SaveWaifuPicture.Result.Success -> state.copy(filename = result.filename)
                }
            }
        }
    }

    fun reset() {
        _state.update { state ->
            state.copy(filename = null)
        }
    }

}
