package com.release.architecture.base

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.release.architecture.base.app.LoadModuleProxy
import com.release.architecture.base.app.ActivityLifecycleCallbacksImpl
import com.release.architecture.base.constant.LogTAG
import com.release.architecture.base.utils.logD
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

/**
 * 自定义 Application 基类
 *
 * @author yancheng
 * @since 2021/11/15
 */
open class BaseApp : MultiDexApplication() {

    private val mLoadModuleProxy by lazy(mode = LazyThreadSafetyMode.NONE) { LoadModuleProxy() }

    companion object {
        // 全局Context
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var application: BaseApp

        // 全局CoroutineScope
        val mCoroutineScope by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            CoroutineScope(
                SupervisorJob()
                        + Dispatchers.Default
                        + CoroutineName("BaseApplicationJob")
                        + CoroutineExceptionHandler { _, throwable ->
                    Log.d(LogTAG.APP_CREATE_TAG, throwable.message ?: "error")
                    throwable.printStackTrace()
                })
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
        application = this
        mLoadModuleProxy.onAttachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()

        // 全局监听 Activity 生命周期
        registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())

        mLoadModuleProxy.onCreate(this)

        // 策略初始化第三方依赖
        initDepends()
    }

    /**
     * 初始化第三方依赖
     */
    private fun initDepends() {
        // 开启一个 Default Coroutine 进行初始化不会立即使用的第三方
        mCoroutineScope.launch(Dispatchers.Default) {
            mLoadModuleProxy.initByBackstage()
        }

        // 前台初始化
        val allTimeMillis = measureTimeMillis {
            val depends = mLoadModuleProxy.initByFrontDesk()
            var dependInfo: String
            depends.forEach {
                val dependTimeMillis = measureTimeMillis { dependInfo = it() }
                Log.d(LogTAG.APP_CREATE_TAG, "initDepends: $dependInfo : $dependTimeMillis ms")
            }
        }
        logD("初始化完成 initApplication $allTimeMillis ms")
    }

    override fun onTerminate() {
        super.onTerminate()
        mLoadModuleProxy.onTerminate(this)
        mCoroutineScope.cancel()
    }
}