package me.ghostbear.composewaifu.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import me.ghostbear.composewaifu.ui.components.ErrorScreen
import me.ghostbear.composewaifu.ui.components.LoadingScreen
import me.ghostbear.composewaifu.ui.gallery.WaifuImage

@Composable
fun FavoriteScreen(vm: FavoriteViewModel, onClickPicture: (String) -> Unit) {

    val state by vm.state.collectAsState()

    when {
        state.hasError -> {
            ErrorScreen(exception = state.error!!)
        }
        state.isLoading -> {
            LoadingScreen()
        }
        else -> {
            LazyColumn {
                items(state.favorites!!) { waifu ->
                    WaifuImage(
                        data = waifu.url,
                        isFavorite = true,
                        onClickImage = { onClickPicture(waifu.url) },
                        onClickFavorite = {
                            vm.removeFavorite(waifu)
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.getFavorites()
    }
}

