package com.example.forgroundservice

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class ServiceApp: Application() {
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "channel_id",
                "notification_name",
                NotificationManager.IMPORTANCE_HIGH,
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}