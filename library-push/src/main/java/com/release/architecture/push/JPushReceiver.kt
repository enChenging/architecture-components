package com.release.architecture.push

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.jpush.android.api.JPushInterface

/**
 * 接收极光推送的自定义广播接收者
 * 极光版本
 * JPlush = "3.2.0"
 * JCore = "2.0.0"
 * 从JPush3.0.7开始，需要配置继承JPushMessageReceiver的广播，原来如果配了MyReceiver现在可以弃用。
 * @author yancheng
 * @since 2021/8/6
 */
class JPushReceiver : BroadcastReceiver() {

    companion object{
        const val TAG = "JPushReceiver"
    }

    private var mRegId: String = ""

    override fun onReceive(context: Context, intent: Intent) {
        intent.run {
            val bundle = extras
            when (action) {
                JPushInterface.ACTION_REGISTRATION_ID -> {
                    mRegId = bundle?.getString(JPushInterface.EXTRA_REGISTRATION_ID) ?: ""
                    Log.i("TAG","[MyReceiver] 接收Registration Id : $mRegId")
                }
                JPushInterface.ACTION_MESSAGE_RECEIVED -> {
                    Log.i("TAG","[MyReceiver] 接收到推送下来的自定义消息: ${bundle?.getString(JPushInterface.EXTRA_MESSAGE)}")
                }
                JPushInterface.ACTION_NOTIFICATION_RECEIVED -> {
                    Log.i("TAG","[MyReceiver] 接收到推送下来的通知 ${bundle?.getString(JPushInterface.EXTRA_EXTRA)}")
                    Log.i("TAG","[MyReceiver] 接收到推送下来的通知的ID: ${bundle?.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)}")

                }
                JPushInterface.ACTION_NOTIFICATION_OPENED -> {
                    Log.i("TAG","[MyReceiver] 用户点击打开了通知")
                }
                JPushInterface.ACTION_RICHPUSH_CALLBACK -> {
                    Log.i("TAG","[MyReceiver] 用户收到到RICH PUSH CALLBACK: ${bundle?.getString(JPushInterface.EXTRA_EXTRA)}")
                    // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等
                }
                JPushInterface.ACTION_CONNECTION_CHANGE -> {
                    Log.i("TAG",
                        "[MyReceiver] connected state change to ${
                            intent.getBooleanExtra(
                                JPushInterface.EXTRA_CONNECTION_CHANGE,
                                false
                            )
                        }"
                    )
                }
            }
            return@run
        }
    }
}