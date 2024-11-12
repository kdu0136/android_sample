package com.dongun.ktouploadfilewithprogressbar

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.UUID

class FileReader(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    suspend fun uriToFileInfo(contentUri: Uri): FileInfo {
        return withContext(ioDispatcher) {
            val bytes = context.contentResolver.openInputStream(contentUri)?.use { inputStream ->
                ByteArrayOutputStream().use { outputStream ->
                    var byte = inputStream.read()
                    while (byte != -1) {
                        outputStream.write(byte)
                        byte = inputStream.read()

                        ensureActive()
                    }
                    outputStream.toByteArray()
                }
            } ?: byteArrayOf()

            val fileName = UUID.randomUUID().toString()
            val mimeType = context.contentResolver.getType(contentUri) ?: ""

            FileInfo(
                name = fileName,
                mimeType = mimeType,
                bytes = bytes
            )
        }
    }
}

class FileInfo(
    val name: String,
    val mimeType: String,
    val bytes: ByteArray,
)
