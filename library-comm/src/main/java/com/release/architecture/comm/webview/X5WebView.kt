package com.release.architecture.comm.webview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.release.architecture.base.utils.logD
import com.release.architecture.base.widget.StateLayout
import com.release.architecture.comm.R
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

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
        mStateLayout = LayoutInflater.from(context).inflate(
            com.release.architecture.base.R.layout.base_include_status_layout,
            null
        ) as StateLayout
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
            javaScriptEnabled = true //允许js调用
            javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
            databaseEnabled = true //设置可以调用数据库
            domStorageEnabled = true //
            cacheMode = WebSettings.LOAD_NO_CACHE
            loadsImagesAutomatically = true
            allowFileAccess = false //在File域下，能够执行任意的JavaScript代码，同源策略跨域访问能够对私有目录文件进行访问等
            setSupportZoom(false) //支持页面缩放
            builtInZoomControls = false //进行控制缩放
            allowContentAccess = true //是否允许在WebView中访问内容URL（Content Url），默认允许
            setGeolocationEnabled(false) //定位是否可用
            setSupportMultipleWindows(false) //设置WebView是否支持多窗口,如果为true需要实现onCreateWindow(WebView, boolean, boolean, Message)
            loadWithOverviewMode = true //是否允许WebView度超出以概览的方式载入页面，
            defaultTextEncodingName = "GBK"
        }
        this.webViewClient = WebViewClient()
        this.webChromeClient = mChromeClient
        this.view.isClickable = true
    }

    override fun loadUrl(url: String) {
        logD(url, "WebViewUrl")
        super.loadUrl(url)
    }
}