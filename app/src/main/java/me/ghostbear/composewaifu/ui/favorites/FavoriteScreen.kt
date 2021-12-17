package me.ghostbear.composewaifu.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize

@Composable
fun FavoriteScreen(vm: FavoriteViewModel) {
    LazyColumn {
        items(vm.favorites) { favorite ->
            val painter = rememberImagePainter(
                data = favorite.url,
                builder = {
                    size(OriginalSize)
                }
            )
            val state = painter.state
            if (state is ImagePainter.State.Loading) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            vm.removeFromFavorite(favorite)
                        },
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        vm.loadFavorites()
    }
}