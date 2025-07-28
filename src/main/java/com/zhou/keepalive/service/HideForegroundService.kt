package com.zhou.keepalive.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.zhou.keepalive.entity.Constant
import com.zhou.keepalive.entity.NotificationConfig
import com.zhou.keepalive.ext.sMainHandler
import com.zhou.keepalive.ext.setNotification

/**
 * 隐藏前台服务
 */
class HideForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getParcelableExtra<NotificationConfig>(Constant.CACTUS_NOTIFICATION_CONFIG)
            ?.let {
                setNotification(it, true)
            }
        sMainHandler.postDelayed({
            stopForeground(true)
            stopSelf()
        }, 2000)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}