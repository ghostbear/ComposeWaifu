package me.ghostbear.composewaifu.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import dagger.hilt.android.AndroidEntryPoint
import me.ghostbear.composewaifu.ui.gallery.GalleryScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box {
                GalleryScreen()
            }
        }
    }
}
