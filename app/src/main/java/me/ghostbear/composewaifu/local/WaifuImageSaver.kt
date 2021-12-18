package me.ghostbear.composewaifu.local

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import me.ghostbear.composewaifu.domain.model.Waifu

class WaifuImageSaver @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun save(waifu: Waifu): Result {
        val uri = Uri.parse(waifu.url)
        val imageLoader = context.imageLoader
        val request = ImageRequest.Builder(context)
            .data(uri)
            .build()
        val result = imageLoader.execute(request)

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, uri.pathSegments.last())
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        val filePath = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val outputStream = context.contentResolver.openOutputStream(filePath!!)

        val drawable = result.drawable ?: return Result.Error("Failed to fetch image")
        val bitmap = drawable.toBitmap()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream?.close()

        return Result.Success
    }

    sealed class Result  {
        data class Error(val message: String) : Result()
        object Success : Result()
    }
}