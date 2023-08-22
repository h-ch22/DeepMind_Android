package com.cj.deepmind.inspection.helper

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.drawToBitmap
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal suspend fun View.drawBitmapFromView(context: Context, config: Bitmap.Config): Bitmap =
    suspendCoroutine { continuation ->
        doOnLayout { view ->
            val window = (context as? Activity)?.window ?: error("Can't get window from the context")

            Bitmap.createBitmap(1080, 1080, config).apply{
                val (x, y) = IntArray(2).apply{
                    view.getLocationInWindow(this)
                }

                PixelCopy.request(
                    window,
                    getRect(x, y),
                    this,
                    { copyResult ->
                        if(copyResult == PixelCopy.SUCCESS) continuation.resume(this) else continuation.resumeWithException(
                            RuntimeException("Bitmap generation failed")
                        )
                    },
                    Handler(Looper.getMainLooper())
                )
            }
        }
    }

private fun View.getRect(x: Int, y: Int): android.graphics.Rect {
    val viewWidth = this.width
    val viewHeight = this.height
    return android.graphics.Rect(x, y, viewWidth + x, viewHeight + y)
}

internal fun Context.saveImage(bitmap: Bitmap): Uri? {
    var uri: Uri? = null
    try {
        val fileName = System.nanoTime().toString() + ".png"
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            } else {
                val directory =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                val file = File(directory, fileName)
                put(MediaStore.MediaColumns.DATA, file.absolutePath)
            }
        }

        uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        uri?.let {
            contentResolver.openOutputStream(it).use { output ->
                if (output != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.apply {
                    clear()
                    put(MediaStore.Audio.Media.IS_PENDING, 0)
                }
                contentResolver.update(uri, values, null, null)
            }
        }
        return uri
    } catch (e: java.lang.Exception) {
        if (uri != null) {
            // Don't leave an orphan entry in the MediaStore
            contentResolver.delete(uri, null, null)
        }
        throw e
    }
}

class InspectionHelper {
}