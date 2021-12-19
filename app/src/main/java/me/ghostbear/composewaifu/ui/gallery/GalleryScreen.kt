package me.ghostbear.composewaifu.ui.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import me.ghostbear.composewaifu.remote.model.WaifuType
import me.ghostbear.composewaifu.ui.components.Chips
import me.ghostbear.composewaifu.ui.components.ErrorScreen
import me.ghostbear.composewaifu.ui.components.LoadingScreen

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GalleryScreen(vm: GalleryViewModel, onClickPicture: (String) -> Unit) {
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
                item {
                    Row {
                        // Type
                        for (type in WaifuType.values()) {
                            Chips(
                                modifier = Modifier
                                    .clickable {
                                        vm.setType(type)
                                    },
                                selected = type == state.type
                            ) {
                                Text(
                                    text = type.name,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    LazyRow {
                        // Categories
                        items(state.type.categories) { category ->
                            Chips(
                                modifier = Modifier
                                    .clickable {
                                        vm.setCategory(category)
                                    },
                                selected = category == state.category
                            ) {
                                Text(
                                    text = category.name,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }
                    }
                }

                items(state.waifus!!) { waifu ->
                    val painter = rememberImagePainter(
                        data = waifu.url,
                        builder = {
                            size(OriginalSize)
                        }
                    )
                    val painterState = painter.state
                    if (painterState is ImagePainter.State.Loading) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val isFavorite = waifu in state.favorites
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
                                    if (isFavorite) {
                                        vm.removeFavorite(waifu)
                                    } else {
                                        vm.addFavorite(waifu)
                                    }
                                },
                                modifier = Modifier
                                    .padding(8.dp)
                                    .align(Alignment.TopEnd)
                            ) {
                                if (isFavorite) {
                                    Icon(
                                        imageVector = Icons.Outlined.Favorite,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.FavoriteBorder,
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }



    }

    LaunchedEffect(Unit) {
        if (state.waifus == null) {
            vm.getWaifus()
        }
    }
}

