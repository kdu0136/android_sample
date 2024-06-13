package com.example.downloadmanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.downloadmanger.ui.theme.DownloadMangerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val downloader = AndroidDownloader(this)
        downloader.downloadFile("https://pl-coding.com/wp-content/uploads/2022/04/pic-squared.jpg")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DownloadMangerTheme {
            }
        }
    }
}
