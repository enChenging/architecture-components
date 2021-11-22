package com.release.architecture.comm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.release.architecture.base.app.ActivityLifecycleCallbacksImpl
import com.release.architecture.base.app.ActivityStackManager
import com.release.architecture.base.listener.network.AutoRegisterNetListener
import com.release.architecture.base.listener.network.INetworkStateChangeListener
import com.release.architecture.base.mvvm.v.FrameView
import com.release.architecture.base.mvvm.vm.BaseViewModel
import com.release.architecture.base.utils.*

/**
 * 启动页Activity基类 与项目无关
 *
 * @author yancheng
 * @since 2021/9/25
 */
abstract class BaseSplashActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
    FrameView<VB>, INetworkStateChangeListener {

    protected val mBinding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        BindingReflex.reflexViewBinding(javaClass, layoutInflater)
    }

    protected abstract val mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        // ARouter 依赖注入
        ARouter.getInstance().inject(this)
        // 注册EventBus
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)
        setStatusBar()
        mBinding.initView()
        initNetworkListener()
        initObserve()
        initRequestData()
    }

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    open fun setStatusBar() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

    /**
     * 初始化网络状态监听
     * @return Unit
     */
    private fun initNetworkListener() {
        lifecycle.addObserver(AutoRegisterNetListener(this))
    }

    /**
     * 网络类型更改回调
     * @param type Int 网络类型
     * @return Unit
     */
    override fun networkTypeChange(type: Int) {}

    /**
     * 网络连接状态更改回调
     * @param isConnected Boolean 是否已连接
     * @return Unit
     */
    override fun networkConnectChange(isConnected: Boolean) {
        showToast(if (isConnected) "网络已连接" else "网络已断开")
    }

    override fun onResume() {
        super.onResume()
        logD(
            "ActivityStack: ${ActivityStackManager.activityStack}",
            ActivityLifecycleCallbacksImpl.TAG
        )
    }

    override fun onDestroy() {
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
            this
        )
        super.onDestroy()
    }
}