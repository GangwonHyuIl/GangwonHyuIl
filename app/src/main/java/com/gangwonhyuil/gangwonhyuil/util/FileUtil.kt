package com.gangwonhyuil.gangwonhyuil.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.format
import id.zelory.compressor.constraint.size
import java.io.File
import java.io.IOException

class FileUtil(
    private val applicationContext: Context,
) {
    suspend fun uriToFile(uri: Uri): File {
        val fileName = getFileName(uri)
        var file = File(applicationContext.cacheDir, fileName)
        try {
            applicationContext.contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
        } catch (e: IOException) {
            throw Throwable("cannot convert uri to file, error: ${e.stackTrace}")
        }

        return Compressor.compress(applicationContext, file) {
            size(1_048_576) // limit max size to 1MB
            format(Bitmap.CompressFormat.JPEG)
        }
    }

    private fun getFileName(uri: Uri): String {
        var fileName = "default_file_name"
        applicationContext.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            fileName = cursor.getString(nameIndex)
        }
        return fileName
    }
}