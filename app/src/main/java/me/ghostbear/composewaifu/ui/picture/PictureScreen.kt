package me.ghostbear.composewaifu.ui.picture

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize

@Composable
fun PictureScreen(
    controller: NavHostController,
    vm: PictureViewModel,
    waifuUrl: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val painter = rememberImagePainter(
        data = waifuUrl,
        builder = {
            size(OriginalSize)
        }
    )
    Column {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { controller.navigateUp() }) {
                    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
                }
            },
            title = {},
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null)
                }
                IconButton(onClick = {
                    vm.save(waifuUrl)
                }) {
                    Icon(imageVector = Icons.Outlined.Download, contentDescription = null)
                }
            }
        )
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
    }
}