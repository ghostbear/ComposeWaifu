package me.ghostbear.data.waifu.local

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import me.ghostbear.domain.waifu.interactor.SaveWaifuPicture
import me.ghostbear.domain.waifu.model.Waifu
import javax.inject.Inject

class WaifuImageSaver @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun save(waifu: Waifu): SaveWaifuPicture.Result {
        val uri = Uri.parse(waifu.url)
        val imageLoader = context.imageLoader
        val request = ImageRequest.Builder(context)
            .data(uri)
            .build()
        val result = imageLoader.execute(request)

        val filename = uri.pathSegments.last()

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, filename)
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())

        val filePath = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val outputStream = context.contentResolver.openOutputStream(filePath!!)

        val drawable = result.drawable ?: return SaveWaifuPicture.Result.Error(Exception("Failed to fetch image"))
        val bitmap = drawable.toBitmap()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream?.close()

        return SaveWaifuPicture.Result.Success(filename)
    }
}