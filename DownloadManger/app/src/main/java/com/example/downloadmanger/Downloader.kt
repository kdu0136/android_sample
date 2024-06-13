package com.example.downloadmanger

interface Downloader {
    fun downloadFile(url: String): Long
}