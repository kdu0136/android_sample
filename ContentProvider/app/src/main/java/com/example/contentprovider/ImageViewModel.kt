package com.example.contentprovider

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {
    var images by mutableStateOf(emptyList<Image>())
        private set

    fun updateImage(images: List<Image>) {
        this.images = images
    }
}