package me.ghostbear.composewaifu.ui.picture

import android.content.ContentValues
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import java.net.URLDecoder
import java.nio.charset.Charset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PictureScreen(
    controller: NavHostController,
    pictureViewModel: PictureViewModel,
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
                    scope.launch(Dispatchers.IO) {
                        val url = URLDecoder.decode(waifuUrl, Charset.defaultCharset().name()).toUri()
                        val values = ContentValues()
                        values.put(MediaStore.Images.Media.TITLE, url.pathSegments.last())
                        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

                        val filePath = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                        val outputStream = context.contentResolver.openOutputStream(filePath!!)

                        val state = painter.state
                        if (state is ImagePainter.State.Success) {
                            val bitmap = state.result.drawable.toBitmap()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                            outputStream?.close()
                            scope.launch(Dispatchers.Main) {
                                Toast.makeText(context, "Image saved to Picture folder", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
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