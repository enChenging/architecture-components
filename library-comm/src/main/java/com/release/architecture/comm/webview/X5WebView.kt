package com.release.architecture.comm.webview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.release.architecture.base.utils.logD
import com.release.architecture.base.widget.StateLayout
import com.release.architecture.comm.R

/**
 * 自定义x5WebView
 * @author yancheng
 * @since 2021/7/29
 */
class X5WebView(context: Context) : WebView(context) {

    /**
     * 自定义WebView加载进度条
     */
    private lateinit var mProgressView: ProgressView

    /**
     * 状态布局
     */
    private lateinit var mStateLayout: StateLayout

    /**
     * 进度条动态改变
     */
    private val mChromeClient: WebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(webView: WebView, progress: Int) {
            super.onProgressChanged(webView, progress)
            mProgressView.setProgress(progress)
            if (progress == 100)
                mStateLayout.hide()
        }
    }

    init {
        initProgressBar()
        initLoadAni()
        initWebViewSettings()
        this.webChromeClient = mChromeClient
        this.view.isClickable = true
    }

    /**
     * 加载进度条
     */
    private fun initProgressBar() {
        mProgressView = ProgressView(context)
        mProgressView.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6)
        mProgressView.setDefaultColor(context.resources.getColor(R.color.comm_theme))
        addView(mProgressView)
    }

    /**
     * 初始化加载动画
     */
    private fun initLoadAni() {
        mStateLayout = LayoutInflater.from(context).inflate(com.release.architecture.base.R.layout.base_include_status_layout, null) as StateLayout
        mStateLayout.layoutParams =
            LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        mStateLayout.setBgColor(0x4D000000)
        mStateLayout.emptyStatus = StateLayout.STATUS_LOADING
        addView(mStateLayout)
    }

    /**
     * 初始化setting配置
     */
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebViewSettings() {
        // 关掉长按事件  避免跳出界面
        setOnLongClickListener { true }

        this.settings.apply {
            javaScriptEnabled = true    // 允许js调用
            javaScriptCanOpenWindowsAutomatically = true    //支持通过JS打开新窗口 弹窗
            allowFileAccess = false // 禁止访问文件
            setSupportZoom(false)   // 设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true，支持缩放
            builtInZoomControls = false // 设置WebView是否使用其内置的变焦机制，该机制集合屏幕缩放控件使用，默认是false
            allowContentAccess = true //是否允许在WebView中访问内容URL（Content Url），默认允许
            setSupportMultipleWindows(false) //设置WebView是否支持多窗口,如果为true需要实现onCreateWindow(WebView, boolean, boolean, Message)
            setGeolocationEnabled(false) // 定位是否可用
            loadWithOverviewMode = true // 是否允许WebView度超出以概览的方式载入页面，
            setAllowUniversalAccessFromFileURLs(false) // 是否允许运行在一个file schema URL环境下的JavaScript访问来自其他任何来源的内容
            setAllowFileAccessFromFileURLs(false) // 是否允许运行在一个URL环境（the context of a file scheme URL）中的JavaScript访问来自其他URL环境的内容，为了保证安全，应该不允许。
        }
        this.webViewClient = WebViewClient()
    }

    override fun loadUrl(url: String) {
        logD(url, "WebViewUrl")
        super.loadUrl(url)
    }


}