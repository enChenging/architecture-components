package com.release.architecture.comm.net.helper

import com.release.architecture.base.utils.SpUtils
import com.release.architecture.comm.constant.SpKey

/**
 * token管理者
 *
 */
object TokenManager {

    private var mToken: String = SpUtils.getString(SpKey.TOKEN, "")

    private var mLastRequestTime: Long = SpUtils.getLong(SpKey.LAST_REQUEST_TIME, 0L)

    /**
     * 从缓存中获取token
     * @return String token
     */
    fun getToken(): String = mToken

    /**
     * 将token保存至MMKV，并刷新缓存到内存的token
     *
     * @param token String 新的token
     * @return Unit
     */
    fun saveToken(token: String) {
        mToken = token
        SpUtils.putString(SpKey.TOKEN, token)
    }

    /**
     * 获取上次的请求时间
     * @return Long
     */
    fun getLastRequestTime(): Long = mLastRequestTime

    /**
     * 刷新请求时间
     * @param time Long 当前时间的毫秒时间戳
     * @return Unit
     */
    fun refreshTime(time: Long) {
        // 更新缓存的时间
        mLastRequestTime = time
        // 更新持久化时间
        SpUtils.putLong(SpKey.LAST_REQUEST_TIME, time)
    }
}