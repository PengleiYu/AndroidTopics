package com.example.notificationdemo

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /**
     * 重要，每个通知必须指定频道id
     */
    private val channelId = "ChannelId"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_createNotification.setOnClickListener(this::createNotification)
    }

    private fun createNotification(view: View) {
        createNotificationChannel()

        // api26
        val builder = Notification.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
            .setContentTitle("Content Title")
            .setContentText("Content Text")
            .setWhen(System.currentTimeMillis() - 1 * 60 * 60 * 1000)
            .setShowWhen(true)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(notifyId, builder.build())
        }
    }

    private var notifyId = 1

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ChannelName"
            val descriptionText = "ChannelDescription"
            val important = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, important).apply { description = descriptionText }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
