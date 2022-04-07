package com.release.architecture.common.base

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.release.architecture.base.mvvm.v.BaseFrameActivity
import com.release.architecture.base.mvvm.vm.BaseViewModel
import com.release.architecture.base.utils.AndroidBugFixUtils
import com.release.architecture.base.utils.logI
import com.release.architecture.common.R
import com.release.architecture.common.webview.X5WebView

/**
 * WebViewActivity基类
 *
 * @author yancheng
 * @since 2021/11/15
 */
abstract class BaseWebActivity<VB : ViewBinding, VM : BaseViewModel> : BaseFrameActivity<VB, VM>() {

    protected var mWebView: X5WebView? = null
    var mTitleTv: TextView? = null
    var mTitleRightTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideTheBottomNavigationBar()
    }

    /**
     * 初始化顶部toolBar
     */
    override fun initToolBar() {
        findViewById<ImageView>(R.id.vBackIv)?.setOnClickListener { onBack() }
        mTitleTv = findViewById(R.id.vTitleTv)
        mTitleRightTv = findViewById(R.id.vTitleRightTv)
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
    private fun backOrFinish() {
        if (mWebView?.canGoBack() == true) {
            logI("canGoBack------------")
            mWebView?.goBack()
        } else {
            logI("onBack------------")
            onBack()
        }
    }

    protected open fun onBack(){
        finish()
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
}