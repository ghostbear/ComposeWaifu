package me.ghostbear.composewaifu.ui.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize

@Composable
fun FavoriteScreen(vm: FavoriteViewModel, onClickPicture: (String) -> Unit) {
    val favorites = vm.favorites.collectAsState(initial = emptyList())

    LazyColumn {
        items(favorites.value) { waifu ->
            val painter = rememberImagePainter(
                data = waifu.url,
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
                Box {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { onClickPicture(waifu.url) }),
                        contentScale = ContentScale.Crop,
                    )
                    IconButton(
                        onClick = {
                            vm.removeFavorite(waifu)
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }
            }
        }
    }
}