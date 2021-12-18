package me.ghostbear.composewaifu.ui.picture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ghostbear.composewaifu.domain.interactor.FindWaifu
import me.ghostbear.composewaifu.domain.interactor.SaveWaifuPicture

@HiltViewModel
class PictureViewModel @Inject constructor(
    private val findWaifu: FindWaifu,
    private val saveWaifuPicture: SaveWaifuPicture
) : ViewModel() {

    fun save(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val waifu = findWaifu.await(url)
            saveWaifuPicture.await(waifu)
        }
    }

}