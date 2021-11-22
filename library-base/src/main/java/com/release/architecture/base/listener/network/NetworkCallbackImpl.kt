package com.release.architecture.base.listener.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 实时监听网络状态变化的[ConnectivityManager.NetworkCallback]实现类
 *
 * @author yancheng
 * @since 2021/11/15
 */
@Singleton
class NetworkCallbackImpl @Inject constructor() : ConnectivityManager.NetworkCallback() {

    /**
     * 当前网络类型
     */
    var currentNetworkType = -1

    /**
     * 当前网络是否已连接
     */
    var isConnected = false

    /**
     * 注册的监听
     */
    var changeCall: INetworkStateChangeListener? = null

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        isConnected = true
        changeCall?.networkConnectChange(isConnected)
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        isConnected = false
        changeCall?.networkConnectChange(isConnected)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            currentNetworkType = when {
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    NetworkCapabilities.TRANSPORT_WIFI
                }
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    NetworkCapabilities.TRANSPORT_CELLULAR
                }
                else -> {
                    NetworkCapabilities.TRANSPORT_WIFI
                }
            }
            changeCall?.networkTypeChange(currentNetworkType)
        }
    }
}