package me.ghostbear.ui.picture

import android.widget.Toast
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import me.ghostbear.ui.common.components.ErrorScreen
import me.ghostbear.ui.common.components.LoadingScreen
import me.ghostbear.ui.common.components.WaifuImage

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
                WaifuImage(
                    data = state.waifu!!.url,
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        vm.getWaifu(waifuUrl)
    }
}