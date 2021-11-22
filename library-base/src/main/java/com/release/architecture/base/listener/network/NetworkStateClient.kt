package com.release.architecture.base.listener.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission

/**
 * 网络状态监听
 *
 * @author yancheng
 * @since 2021/11/15
 */
object NetworkStateClient {

    private val mNetworkCallback = NetworkCallbackImpl()

    /**
     * 注册网络监听客户端
     * @return Unit
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    fun register() {
        val build = NetworkRequest.Builder().build()
        val cm =
            com.release.architecture.base.BaseApp.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.registerNetworkCallback(build, mNetworkCallback)
    }

    /**
     * 设置监听器
     * @param listener NetworkStateChangeListener 监听器
     * @return Unit
     */
    fun setListener(listener: INetworkStateChangeListener) {
        mNetworkCallback.changeCall = listener
    }

    /**
     * 移除监听器
     * @return Unit
     */
    fun removeListener() {
        mNetworkCallback.changeCall = null
    }

    /**
     * 获取网络类型 其他类型的网络归为了WIFI，如果需要细分，可以对[NetworkCallbackImpl]进行追加分类
     * @return Int WIFI:[NetworkCapabilities.TRANSPORT_WIFI]、移动网络:[NetworkCapabilities.TRANSPORT_CELLULAR]
     */
    fun getNetworkType(): Int = mNetworkCallback.currentNetworkType

    /**
     * 网络是否连接
     * @return Boolean
     */
    fun isConnected(): Boolean = mNetworkCallback.isConnected
}