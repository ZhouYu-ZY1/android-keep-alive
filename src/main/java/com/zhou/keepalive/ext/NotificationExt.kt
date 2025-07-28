package com.zhou.keepalive.ext

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zhou.keepalive.R
import com.zhou.keepalive.entity.Constant
import com.zhou.keepalive.entity.NotificationConfig
import com.zhou.keepalive.service.HideForegroundService

/**
 * @author geyifeng
 * @date 2019-11-16 18:01
 */

/**
 * 小图标
 */
private val NotificationConfig.handleSmallIcon
    get() = if (hideNotification) {
        if (!hideNotificationAfterO) {
            smallIcon
        } else {
            R.drawable.icon_cactus_small
        }
    } else {
        smallIcon
    }

/**
 * 设置通知栏信息
 *
 * @receiver Service
 * @param notificationConfig NotificationConfig
 */
internal fun Service.setNotification(
    notificationConfig: NotificationConfig,
    isHideService: Boolean = false
) {
    val managerCompat = NotificationManagerCompat.from(this)
    val notification = getNotification(notificationConfig)
    notificationConfig.apply {
        //更新Notification
        managerCompat.notify(serviceId, notification)
        //设置前台服务Notification
        startForeground(serviceId, notification)
        //隐藏Notification
        if (hideNotification) {
            if (managerCompat.getNotificationChannel(notification.channelId) != null
                && hideNotificationAfterO
            ) {
                sMainHandler.postDelayed(
                    {
                        managerCompat.deleteNotificationChannel(notification.channelId)
                    }, 1000
                )
            }
        }
    }
}

/**
 * 获得Notification
 *
 * @param notificationConfig NotificationConfig
 * @return Notification
 */
internal fun Context.getNotification(notificationConfig: NotificationConfig): Notification =
    notificationConfig.run {
        val managerCompat = NotificationManagerCompat.from(this@getNotification)
        //构建Notification
        val notification =
            notification ?: NotificationCompat.Builder(this@getNotification, channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(handleSmallIcon)
                .setLargeIcon(
                    largeIconBitmap ?: if (largeIcon == 0) {
                        null
                    } else {
                        BitmapFactory.decodeResource(
                            resources,
                            largeIcon
                        )
                    }
                )
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(NotificationCompat.VISIBILITY_SECRET)
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .apply {
                    remoteViews?.also {
                        setContent(it)
                    }
                    bigRemoteViews?.also {
                        setCustomBigContentView(it)
                    }
                }
                .build()
        //设置渠道
        if (managerCompat.getNotificationChannel(notification.channelId) == null) {
            if (notificationChannel != null && notificationChannel is NotificationChannel) {
                (notificationChannel as NotificationChannel).apply {
                    if (id != notification.channelId) {
                        return@apply
                    }
                }
            } else {
                notificationChannel = NotificationChannel(
                    notification.channelId,
                    notificationConfig.channelName,
                    NotificationManager.IMPORTANCE_NONE
                )
            }
            managerCompat.createNotificationChannel(notificationChannel as NotificationChannel)
        }
        notification
    }