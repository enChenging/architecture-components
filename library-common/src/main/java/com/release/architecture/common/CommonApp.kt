package com.release.architecture.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.alibaba.android.arouter.launcher.ARouter
import com.google.auto.service.AutoService
import com.luck.picture.lib.PicturePreviewActivity
import com.luck.picture.lib.PictureSelectorActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.release.architecture.base.BaseApp
import com.release.architecture.base.app.ApplicationLifecycle
import com.release.architecture.base.constant.LogTAG
import com.release.architecture.base.constant.VersionStatus
import com.release.architecture.base.listener.network.NetworkStateClient
import com.release.architecture.base.utils.ProcessUtils
import com.release.architecture.base.utils.SpUtils
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import me.jessyan.autosize.AutoSizeConfig

/**
 * 公共模块的伪 Application
 *
 * @author yancheng
 * @since 2021/11/15
 */
@AutoService(ApplicationLifecycle::class)
class CommonApp : ApplicationLifecycle {

    companion object {
        //静态代码段可以防止内存泄露
        init {
            //设置全局的Header构建器
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(android.R.color.white, android.R.color.black) //全局设置主题颜色
                ClassicsHeader(context) //.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
            //设置全局的Footer构建器
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout -> //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }

        // 全局CommonApplication
        @SuppressLint("StaticFieldLeak")
        lateinit var mCommonApplication: CommonApp
    }

    override fun onAttachBaseContext(context: Context) {
        mCommonApplication = this
    }

    override fun onCreate(application: Application) {}

    override fun onTerminate(application: Application) {}

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(BaseApp.context)) {
            list.add { initARouter() }
            list.add { initAndroidAutoSize() }
            list.add { initMMKV() }
            list.add { initLogger() }
            list.add { initNetworkStateClient() }
            list.add { initPush() }
        }
        list.add { initTencentBugly() }
        return list
    }

    /**
     * 不立即使用的后台线程进行初始化
     */
    override fun initByBackstage() {
        initX5WebViewCore()
    }

    /**
     * 阿里路由 ARouter 初始化
     */
    private fun initARouter(): String {
        // 测试环境下打开ARouter的日志和调试模式 正式环境需要关闭
        if (BuildConfig.VERSION_TYPE != VersionStatus.RELEASE) {
            // 打印日志
            ARouter.openLog()
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug()
        }
        ARouter.init(BaseApp.application)
        Log.d(LogTAG.APP_CREATE_TAG, "ARouter -->> init complete")
        return "ARouter -->> init complete"
    }

    /**
     * 屏幕适配 AndroidAutoSize
     */
    private fun initAndroidAutoSize(): String {
        AutoSizeConfig.getInstance().run {
            isBaseOnWidth = true
            setLog(true)
            externalAdaptManager.run {
                // 将不需要适配的第三方库 Activity 添加进来 (但不局限于三方库)
                // 即可让该 Activity 的适配效果失效
                addCancelAdaptOfActivity(PictureSelectorActivity::class.java)
                addCancelAdaptOfActivity(PicturePreviewActivity::class.java)
            }
        }
        Log.d(LogTAG.APP_CREATE_TAG, "AndroidAutoSizeLog -->> init complete")
        return "AndroidAutoSizeLog -->> init complete"
    }

    /**
     * 初始化 腾讯Bugly
     * 测试环境应该与正式环境的日志收集渠道分隔开
     * 目前有两个渠道 测试版本/正式版本
     */
    private fun initTencentBugly(): String {
        val appId = when (BuildConfig.VERSION_TYPE) {
            VersionStatus.DEBUG -> {
                BaseApp.context.getString(R.string.BUGLY_APP_ID_DEV)
            }
            VersionStatus.BETA -> {
                BaseApp.context.getString(R.string.BUGLY_APP_ID_TEST)
            }
            VersionStatus.RELEASE -> {
                BaseApp.context.getString(R.string.BUGLY_APP_ID_PROD)
            }
            else -> BaseApp.context.getString(R.string.BUGLY_APP_ID_DEV)
        }
        Log.d(LogTAG.APP_CREATE_TAG, "initTencentBugly: appId:$appId")

        // 如果App使用了多进程且各个进程都会初始化Bugly（例如在Application类onCreate()中初始化Bugly）
        // 那么每个进程下的Bugly都会进行数据上报，造成不必要的资源浪费。
        // 因此，为了节省流量、内存等资源，建议初始化的时候对上报进程进行控制，只在主进程下上报数据
        // 判断是否是主进程（通过进程名是否为包名来判断），并在初始化Bugly时增加一个上报进程的策略配置。
        // 设置上报进程为主进程
        val userStrategy = CrashReport.UserStrategy(BaseApp.context).apply {
            // 设置是否是上传进程
            isUploadProcess = ProcessUtils.isMainProcess(BaseApp.context)
            // 设置渠道
            appChannel = BaseApp.context.getString(com.release.architecture.common.R.string.CHANNEL)
            // 设置 联网上传数据的延迟时间
            appReportDelay = 15L * 1000L
        }
        // 第三个参数为SDK调试模式开关
        CrashReport.initCrashReport(
            BaseApp.context,
            BaseApp.context.getString(R.string.BUGLY_APP_ID_PROD),
            BuildConfig.VERSION_TYPE != VersionStatus.RELEASE,
            userStrategy
        )
        Log.d(LogTAG.APP_CREATE_TAG, "Bugly -->> init complete")
        return "Bugly -->> init complete"
    }

    /**
     * 腾讯 MMKV 初始化
     */
    private fun initMMKV(): String {
        val result = SpUtils.initMMKV(BaseApp.context)
        Log.d(LogTAG.APP_CREATE_TAG, "MMKV -->> $result")
        return "MMKV -->> $result"
    }

    /**
     * 初始化网络状态监听客户端
     * @return Unit
     */
    @SuppressLint("MissingPermission")
    private fun initNetworkStateClient(): String {
        NetworkStateClient.register()
        Log.d(LogTAG.APP_CREATE_TAG, "NetworkStateClient -->>init complete")
        return "NetworkStateClient -->> init complete"
    }

    /**
     * 日志打印 初始化
     */
    private fun initLogger(): String {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true)       // 是否显示线程信息
            .methodCount(2)             // 打印的方法栈深度
            .methodOffset(1)            // 方法偏移
            .tag(LogTAG.DEF_PROJECT_TAG)    // 默认的全局TAG
            .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.VERSION_TYPE != VersionStatus.RELEASE
            }
        })
        Log.d(LogTAG.APP_CREATE_TAG, "Logger -->> init complete")
        return "Logger -->> init complete"
    }

    /**
     * 初始化极光推送
     */
    private fun initPush(): String {
        // 调试模式
        JPushInterface.setDebugMode(BuildConfig.VERSION_TYPE != VersionStatus.RELEASE)
        // 初始化推送
        JPushInterface.init(BaseApp.application)
        Log.d(LogTAG.APP_CREATE_TAG, "JPush -->> init complete")
        return "JPush -->> init complete"
    }

    /**
     * 腾讯TBS WebView X5 内核初始化
     */
    private fun initX5WebViewCore() {
        // dex2oat优化方案
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        // 允许使用非wifi网络进行下载
        QbSdk.setDownloadWithoutWifi(true)

        // 初始化
        QbSdk.initX5Environment(BaseApp.context, object : QbSdk.PreInitCallback {

            override fun onCoreInitFinished() {
                Log.d(LogTAG.APP_CREATE_TAG, " TBS X5 init finished")
            }

            override fun onViewInitFinished(isSuccee: Boolean) {
                // 初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核
                Log.d(LogTAG.APP_CREATE_TAG, "TBS X5 init is $isSuccee")
            }
        })
    }
}