package com.dongun.imagecompression

import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch


@Composable
fun PhotoPickerScreen(
    modifier: Modifier = Modifier,
    imageCompressor: ImageCompressor,
    fileManager: FileManager,
) {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { contentUri ->
        if (contentUri == null) {
            return@rememberLauncherForActivityResult
        }

        val mimeType = context.contentResolver.getType(contentUri)
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        scope.launch {
            fileManager.saveImage(
                contentUri = contentUri,
                fileName = "uncompressed.$extension",
            )
        }

        scope.launch {
            val compressedImage = imageCompressor.compressImage(
                contentUri = contentUri,
                compressionThreshold = 200 * 1024L,
            ) ?: return@launch
            fileManager.saveImage(
                bytes = compressedImage,
                fileName = "compressed.$extension",
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
        ) {
            Text("Pick an Image")
        }
    }
}