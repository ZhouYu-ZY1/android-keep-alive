package com.zhou.keepalive.callback

/**
 * 监听回调
 */
interface CactusCallback {

    /**
     * do something
     * @param times Int 连接次数
     */
    fun doWork(times: Int)

    /**
     * 停止时调用
     */
    fun onStop()
}