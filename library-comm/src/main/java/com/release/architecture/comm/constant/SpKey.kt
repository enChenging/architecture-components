package com.release.architecture.comm.constant

/**
 * 本地存储的键 放在此类中
 *
 * @author yancheng
 * @since 2021/11/15
 */
object SpKey{
    const val TOKEN = "TOKEN"   // 用户token

    const val LAST_REQUEST_TIME = "LAST_REQUEST_TIME"  // 上次请求时间 用于计算token失效 token失效时间为上次请求距离现在7*24h
}