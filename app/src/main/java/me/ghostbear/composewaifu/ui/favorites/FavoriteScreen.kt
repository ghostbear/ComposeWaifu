package me.ghostbear.composewaifu.ui.favorites

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

