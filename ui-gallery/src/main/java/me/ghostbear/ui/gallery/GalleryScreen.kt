package me.ghostbear.ui.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import me.ghostbear.ui.common.components.Chips
import me.ghostbear.ui.common.components.ErrorScreen
import me.ghostbear.ui.common.components.LoadingScreen
import me.ghostbear.domain.waifu.model.WaifuType
import me.ghostbear.ui.common.components.WaifuImage

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
                    WaifuImage(
                        data = waifu.url,
                        isFavorite = waifu.isFavorite,
                        onClickImage = { onClickPicture(waifu.url) },
                        onClickFavorite = {
                            vm.toggleFavorite(waifu)
                        }
                    )
                }
            }
        }
    }
}

