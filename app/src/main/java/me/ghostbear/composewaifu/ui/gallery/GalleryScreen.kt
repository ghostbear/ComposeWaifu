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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import me.ghostbear.composewaifu.data.model.WaifuType
import me.ghostbear.composewaifu.ui.components.Chips

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GalleryScreen() {
    val vm: GalleryViewModel = viewModel()
    LaunchedEffect(vm.selectedType, vm.selectedCategory) {
        vm.loadImage()
    }

    LazyColumn {

        item {
            Row {
                // Type
                for (type in WaifuType.values()) {
                    Chips(
                        modifier = Modifier
                            .clickable {
                                vm.selectedType = type
                                vm.selectedCategory = type.categories.first()
                            },
                        selected = type == vm.selectedType
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
                items(vm.selectedType.categories) { category ->
                    Chips(
                        modifier = Modifier
                            .clickable {
                                vm.selectedCategory = category
                            },
                        selected = category == vm.selectedCategory
                    ) {
                        Text(
                            text = category.name,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

        items(vm.waifuCollection.files) { url ->
            val painter = rememberImagePainter(
                data = url,
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
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
            }

        }
    }
}

