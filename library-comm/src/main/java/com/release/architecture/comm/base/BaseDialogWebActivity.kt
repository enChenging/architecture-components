package com.release.architecture.comm.base

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.release.architecture.base.utils.AndroidBugFixUtils
import com.release.architecture.base.utils.BarUtils
import com.release.architecture.base.utils.EventBusRegister
import com.release.architecture.base.utils.EventBusUtils
import com.release.architecture.comm.R
import com.release.architecture.comm.webview.X5WebView

/**
 * DialogWebViewActivity基类
 *
 * @author yancheng
 * @since 2021/11/15
 */
abstract class BaseDialogWebActivity : Activity() {

    protected var mWebView: X5WebView? = null

    protected var mTitleTv: TextView? = null

    protected var mTitleRightTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ARouter 依赖注入
        ARouter.getInstance().inject(this)
        // 注册EventBus
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)
        setStatusBar()
        initToolBar()
        hideTheBottomNavigationBar()
    }

    /**
     * 设置状态栏
     * 子类需要自定义时重写该方法即可
     * @return Unit
     */
    private fun setStatusBar() {
        BarUtils.setStatusBarColor(this, Color.WHITE)
        BarUtils.setStatusBarLightMode(this, true)
    }

    /**
     * 初始化顶部toolBar
     */
    private fun initToolBar(){
        findViewById<ImageView>(R.id.vBackIv)?.setOnClickListener { backOrFinish() }
        mTitleTv = findViewById(R.id.vTitleTv)
        mTitleRightTv = findViewById(R.id.vTitleRightTv)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && mWebView?.canGoBack() == true) {
            //返回键监听 回滚H5页面
            mWebView?.goBack()
            false
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    /**
     * 返回或关闭当前页，如果当前Web页面能够返回上一级就返回上一级，否则就关闭页面
     */
    protected fun backOrFinish() {
        if (mWebView?.canGoBack() == true) {
            mWebView?.goBack()
        } else {
            finish()
        }
    }

    /**
     * 隐藏底部导航栏
     * @return Unit
     */
    protected open fun hideTheBottomNavigationBar() {
        // 隐藏底部按键
        val params = window.attributes
        params.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window.attributes = params
    }

    override fun onDestroy() {
        // 解决某些特定机型会触发的Android本身的Bug
        AndroidBugFixUtils().fixSoftInputLeaks(this)
        super.onDestroy()
        mWebView?.run {
            //加载null内容
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            //清除历史记录
            clearHistory()
            clearCache(true)
            clearFormData()
            //移除WebView
            (parent as ViewGroup).removeView(mWebView)
            destroy()
            mWebView = null
        }
    }
}