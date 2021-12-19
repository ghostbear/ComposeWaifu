package me.ghostbear.composewaifu.ui.picture

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Download
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import me.ghostbear.composewaifu.ui.components.ErrorScreen
import me.ghostbear.composewaifu.ui.components.LoadingScreen

@Composable
fun PictureScreen(
    controller: NavHostController,
    vm: PictureViewModel,
    waifuUrl: String
) {
    val context = LocalContext.current

    val state by vm.state.collectAsState()

    LaunchedEffect(state.filename) {
        state.filename?.let {
            Toast.makeText(context, "Saved file as $it", Toast.LENGTH_LONG).show()
            vm.reset()
        }
    }

    when {
        state.hasError -> {
            ErrorScreen(exception = state.error!!)
        }
        state.isLoading -> {
            LoadingScreen()
        }
        else -> {
            val painter = rememberImagePainter(
                data = state.waifu!!.url,
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
                        IconButton(onClick = {
                            vm.save()
                        }) {
                            Icon(imageVector = Icons.Outlined.Download, contentDescription = null)
                        }
                    }
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.getWaifu(waifuUrl)
    }
}