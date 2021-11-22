package com.release.architecture.comm.base

import android.content.res.Resources
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.release.architecture.base.mvvm.v.BaseFrameActivity
import com.release.architecture.base.mvvm.vm.BaseViewModel
import com.release.architecture.base.utils.AndroidBugFixUtils
import com.release.architecture.comm.R
import me.jessyan.autosize.AutoSizeCompat

/**
 * Activity 基类
 *
 * @author yancheng
 * @since 2021/11/15
 */
abstract class BaseActivity<VB : ViewBinding, VM : BaseViewModel> : BaseFrameActivity<VB, VM>() {

    var mTitleTv: TextView? = null
    var mTitleRightTv: TextView? = null

    /**
     * 初始化顶部toolBar
     */
    override fun initToolBar() {
        findViewById<ImageView>(R.id.vBackIv)?.setOnClickListener { onBack() }
        mTitleTv = findViewById(R.id.vTitleTv)
        mTitleRightTv = findViewById(R.id.vTitleRightTv)
    }

    protected open fun onBack() {
        finish()
    }

    override fun getResources(): Resources {
        // 主要是为了解决 AndroidAutoSize 在横屏切换时导致适配失效的问题
        // 但是 AutoSizeCompat.autoConvertDensity() 对线程做了判断 导致Coil等图片加载框架在子线程访问的时候会异常
        // 所以在这里加了线程的判断 如果是非主线程 就取消单独的适配
        val res = super.getResources()
        if (Looper.myLooper() == Looper.getMainLooper()) {
            AutoSizeCompat.autoConvertDensityOfGlobal(res)
        }
        return res
    }

    override fun onDestroy() {
        // 解决某些特定机型会触发的Android本身的Bug
        AndroidBugFixUtils().fixSoftInputLeaks(this)
        super.onDestroy()
    }
}