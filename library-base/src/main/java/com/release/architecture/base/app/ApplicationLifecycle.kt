package com.release.architecture.base.app

import android.app.Application
import android.content.Context

/**
 * Application 生命周期 用于初始化各个组件
 *
 * @author yancheng
 * @since 2021/11/15
 */
interface ApplicationLifecycle {

    /**
     * 同[Application.attachBaseContext]
     * @param context Context
     */
    fun onAttachBaseContext(context: Context)

    /**
     * 同[Application.onCreate]
     * @param application Application
     */
    fun onCreate(application: Application)

    /**
     * 同[Application.onTerminate]
     * @param application Application
     */
    fun onTerminate(application: Application)

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    fun initByFrontDesk(): MutableList<() -> String>

    /**
     * 不立即使用的后台线程进行初始化
     */
    fun initByBackstage()
}