# Android 保活服务集成文档

## 功能简介
本组件提供Android应用后台保活能力，支持Android 14，通过：
- 双进程前台服务
- JobScheduler
- onePix(一像素)
- WorkManager
- 无声音乐 

等技术手段提升应用存活率（如果需要被杀后重启拉起，请引导用户开启“自启动”权限）

## 集成步骤

### 添加依赖
1. 将aar文件放入`libs`目录
2. 在build.gradle中添加：

```gradle
dependencies {
    implementation files('libs/keepalive-release-1.0.0.aar')
}
```
初始化配置
在Application中初始化：

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  initKeepAlive()
}

private fun initKeepAlive() {
  val intent = Intent(this, MainActivity::class.java).apply {
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
  }

  val pendingIntent = PendingIntent.getActivity(
    this, 0, intent,
    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
  )

  val channel = NotificationChannel(
    "1001",
    "keep_alive",
    NotificationManager.IMPORTANCE_LOW
  ).apply {
    description = "保障应用正常运行"
    setShowBadge(false)
    setSound(null, null)
    enableVibration(false)
  }

  (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
    .createNotificationChannel(channel)

  // 启动保活服务
  keepAliveService {
    setTitle(getString(R.string.app_name))
    setNotificationChannel(channel)
    setContent("服务正在运行中")
    setPendingIntent(pendingIntent)
    setSmallIcon(R.mipmap.ic_launcher)
    setMusicId(com.zhou.keepalive.R.raw.novioce)
    addCallback({
      // onStop回调，可以省略
      // 保活服务停止
    }) {
      // 保活服务启动成功
      // 在这里处理你的任务
    }
  }
}
```

### 注意事项
提示： 本功能仅限必要场景使用，需明确告知用户并获得授权，禁止用于任何违反法律法规的用途。滥用责任自负。

致谢
本组件基于 https://github.com/gyf-dev/Cactus 二次开发，特别感谢原作者的开源贡献。
