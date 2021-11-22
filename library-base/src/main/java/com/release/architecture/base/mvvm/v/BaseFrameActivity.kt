package com.release.architecture.base.mvvm.v

import android.content.res.Resources
import android.os.Bundle
import android.os.Looper
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.release.architecture.base.R
import com.release.architecture.base.app.ActivityLifecycleCallbacksImpl
import com.release.architecture.base.app.ActivityStackManager
import com.release.architecture.base.ktx.observeStateLayout
import com.release.architecture.base.listener.network.AutoRegisterNetListener
import com.release.architecture.base.listener.network.INetworkStateChangeListener
import com.release.architecture.base.mvvm.vm.BaseViewModel
import com.release.architecture.base.utils.*
import com.release.architecture.base.widget.StateLayout
import me.jessyan.autosize.AutoSizeCompat

/**
 * Activity基类 与业务无关
 *
 * @author yancheng
 * @since 2021/11/15
 */
abstract class BaseFrameActivity<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
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
        initToolBar()
        mBinding.initView()
        initStateLayout()
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
     * 初始化ToolBar
     */
    abstract fun initToolBar()

    /**
     * 初始化状态布局
     */
    open fun initStateLayout() {
        findViewById<StateLayout?>(R.id.vStateLayout)?.run {
            findViewById<ImageView>(R.id.vLoadingIv)?.let {
                Glide.with(context).load(R.drawable.base_ic_loading).into(it)
            }
            observeStateLayout(mViewModel.stateViewLD, this)
        }
    }

    /**
     * 设置状态布局背景颜色
     * @param color Int
     */
    open fun setStateLayoutBg(@ColorInt color: Int) {
        findViewById<StateLayout?>(R.id.vStateLayout)?.setBgColor(color)
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

    override fun getResources(): Resources {
        // 主要是为了解决 AndroidAutoSize 在横屏切换时导致适配失效的问题
        // 但是 AutoSizeCompat.autoConvertDensity() 对线程做了判断 导致Coil等图片加载框架在子线程访问的时候会异常
        // 所以在这里加了线程的判断 如果是非主线程 就取消单独的适配
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal(super.getResources())
        }
        return super.getResources()
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