package me.ghostbear.composewaifu.ui.gallery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.ghostbear.composewaifu.data.WaifuApi
import me.ghostbear.composewaifu.data.model.WaifuCategory
import me.ghostbear.composewaifu.data.model.WaifuCollection
import me.ghostbear.composewaifu.data.model.WaifuType

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val api: WaifuApi
) : ViewModel() {

    var selectedType: WaifuType by mutableStateOf(WaifuType.SFW)
    var selectedCategory: WaifuCategory by mutableStateOf(WaifuCategory.WAIFU)
    var waifuCollection by mutableStateOf(WaifuCollection(listOf()))

    fun loadImage() {
        viewModelScope.launch(Dispatchers.IO) {
            waifuCollection = api.getImages(selectedType, selectedCategory)
        }
    }

}