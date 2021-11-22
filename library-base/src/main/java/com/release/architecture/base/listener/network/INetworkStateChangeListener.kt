package com.release.architecture.base.listener.network

/**
 * 网络状态改变监听器
 *
 * @author yancheng
 * @since 2021/11/15
 */
interface INetworkStateChangeListener {

    /**
     * 网络类型更改回调
     * @param type Int 网络类型
     * @return Unit
     */
    fun networkTypeChange(type: Int)

    /**
     * 网络连接状态更改回调
     * @param isConnected Boolean 是否已连接
     * @return Unit
     */
    fun networkConnectChange(isConnected: Boolean)
}