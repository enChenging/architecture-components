package com.release.architecture.common.ui

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.release.architecture.base.mvvm.vm.EmptyViewModel
import com.release.architecture.common.base.BaseWebActivity
import com.release.architecture.common.constant.RouteKey
import com.release.architecture.common.constant.RouteUrl
import com.release.architecture.common.databinding.CommonActivityWebBinding
import com.release.architecture.common.webview.X5WebView
import dagger.hilt.android.AndroidEntryPoint

/**
 * 公共的 WebViewActivity
 *
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Common.CommonWebActivity)
class CommonWebActivity : BaseWebActivity<CommonActivityWebBinding, EmptyViewModel>() {

    override val mViewModel: EmptyViewModel by viewModels()

    /**
     * 跳转参数(必传) 标题
     */
    @JvmField
    @Autowired(name = RouteKey.KEY_TITLE)
    var title: String = ""

    /**
     * 跳转参数(必传) web地址
     */
    @JvmField
    @Autowired(name = RouteKey.KEY_URL)
    var url: String = ""

    companion object {

        /**
         * 跳转方法
         * @param title String 标题
         * @param url String H5地址
         */
        fun start(title: String, url: String) {
            ARouter.getInstance()
                .build(RouteUrl.Common.CommonWebActivity)
                .withString(RouteKey.KEY_TITLE, title)
                .withString(RouteKey.KEY_URL, url)
                .navigation()
        }
    }

    override fun CommonActivityWebBinding.initView() {
        vTitleBar.run {
            vTitleTv.text = title
            vBackIv.setOnClickListener { finish() }
        }
        initWebView()
    }

    override fun initObserve() {}

    override fun initRequestData() {}

    private fun initWebView() {
        mWebView = X5WebView(this)
        mWebView?.let {
            it.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            mBinding.vContainerFl.addView(it)
        }
        mWebView?.loadUrl(url)
    }
}