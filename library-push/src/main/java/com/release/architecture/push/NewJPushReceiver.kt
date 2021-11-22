package com.release.architecture.push

import android.content.Context
import android.util.Log
import cn.jpush.android.api.CmdMessage
import cn.jpush.android.api.CustomMessage
import cn.jpush.android.api.JPushMessage
import cn.jpush.android.api.NotificationMessage
import cn.jpush.android.service.JPushMessageReceiver
import com.alibaba.fastjson.JSON
import org.greenrobot.eventbus.EventBus

/**
 * 接收极光推送的自定义广播接收者
 * 极光版本
 * JPlush = "4.3.0"
 * JCore = "2.9.0"
 * 从JPush3.0.7开始，需要配置继承JPushMessageReceiver的广播，原来如果配了MyReceiver现在可以弃用。
 * 3.3.0开始所有事件将通过该类回调 -->该广播需要继承 JPush 提供的 JPushMessageReceiver 类, 并如下新增一个 Intent-Filter   <action android:name="cn.jpush.android.intent.RECEIVE_MESSAGE" />
 * @author yancheng
 * @since 2021/9/16
 */
class NewJPushReceiver : JPushMessageReceiver() {

    companion object{
        const val TAG = "NewJPushReceiver"
    }

    private val mEventBus by lazy(mode = LazyThreadSafetyMode.NONE) { EventBus.getDefault() }

    override fun onMessage(context: Context, message: CustomMessage) {
        super.onMessage(context, message)
        Log.i("TAG","onMessage: $message")
    }

    override fun onNotifyMessageOpened(context: Context, message: NotificationMessage) {
        super.onNotifyMessageOpened(context, message)
        Log.i("TAG","onNotifyMessageOpened: $message")
    }

    override fun onNotifyMessageArrived(context: Context, message: NotificationMessage) {
        super.onNotifyMessageArrived(context, message)
        val content = message.notificationContent
        Log.i("TAG","onNotifyMessageArrived: $message \n content: $content")
        try {
            val json = JSON.parseObject(content, JPushBean::class.javaObjectType)
            if ("1" == json.type) {
                mEventBus.post(ClassBeginPushEvent(json.teacherId, json.activityId, json.classId))
            } else if ("2" == json.type) {
                mEventBus.post(ClassIsOverPushEvent(json.teacherId, json.activityId))
            }
            mEventBus.post(ActPushEvent(json.activityId))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onNotifyMessageDismiss(context: Context, message: NotificationMessage) {
        Log.i("TAG","onNotifyMessageDismiss: $message")
    }

    override fun onRegister(context: Context, registrationId: String) {
        super.onRegister(context, registrationId)
        Log.i("TAG","onRegister: $registrationId")
    }

    override fun onConnected(context: Context, isConnected: Boolean) {
        super.onConnected(context, isConnected)
        Log.i("TAG","onConnected: $isConnected")
    }

    override fun onCommandResult(context: Context, cmdMessage: CmdMessage?) {
        super.onCommandResult(context, cmdMessage)
        Log.i("TAG","onCommandResult: $cmdMessage")
    }

    override fun onTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onTagOperatorResult(context, jPushMessage)
        Log.i("TAG","onTagOperatorResult: $jPushMessage")
    }

    override fun onCheckTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onCheckTagOperatorResult(context, jPushMessage)
        Log.i("TAG","onCheckTagOperatorResult: $jPushMessage")
    }

    override fun onAliasOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage)
        Log.i("TAG","onAliasOperatorResult: $jPushMessage")
    }

    override fun onMobileNumberOperatorResult(context: Context, jPushMessage: JPushMessage) {
        super.onMobileNumberOperatorResult(context, jPushMessage)
        Log.i("TAG","onMobileNumberOperatorResult: $jPushMessage")
    }
}