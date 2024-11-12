package com.dongun.ktouploadfilewithprogressbar

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.io.FileNotFoundException
import java.nio.channels.UnresolvedAddressException

class UploadFileViewModel(
    private val repository: FileRepository,
): ViewModel() {
    var state by mutableStateOf(UploadState())
        private set

    private var uploadJob: Job? = null

    fun uploadFile(contentUri: Uri) {
        uploadJob = repository
            .uploadFile(contentUri = contentUri)
            .onStart {
                state = state.copy(
                    isUploading = true,
                    isUploadComplete = false,
                    progress = 0f,
                    errorMessage = null,
                )
            }
            .onEach {
                state = state.copy(
                    progress = it.bytesSent.toFloat() / it.totalBytes,
                )
            }
            .onCompletion { cause ->
                if (cause == null) {
                    state = state.copy(
                        isUploading = false,
                        isUploadComplete = true,
                    )
                } else if (cause is CancellationException) {
                    state = state.copy(
                        isUploading = false,
                        isUploadComplete = false,
                        progress = 0f,
                        errorMessage = "The upload was cancelled!",
                    )
                }
            }
            .catch { cause ->
                val message = when(cause) {
                    is OutOfMemoryError -> "File too large!"
                    is FileNotFoundException -> "File not found!"
                    is UnresolvedAddressException -> "No internet!"
                    else -> "Something went wrong!"
                }
                state = state.copy(
                    isUploading = false,
                    errorMessage = message,
                )
            }
            .launchIn(viewModelScope)
    }

    fun cancelUpload() {
        uploadJob?.cancel()
    }
}

data class UploadState(
    val isUploading: Boolean = false,
    val isUploadComplete: Boolean = false,
    val progress: Float = 0f,
    val errorMessage: String? = null,
)
