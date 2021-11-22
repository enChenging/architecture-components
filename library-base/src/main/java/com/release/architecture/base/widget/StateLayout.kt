package com.release.architecture.base.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.release.architecture.base.R

/**
 * @author yancheng
 * @create 2019/3/22
 * @Describe
 */
class StateLayout : FrameLayout {

    private var mOnRetryListener: OnRetryListener? = null
    private var mEmptyStatus = STATUS_HIDE
    private var mBgColor = 0
    private var msg: String? = null
    private lateinit var mEmptyLayout: FrameLayout
    private lateinit var mTvEmptyMessage: TextView
    private lateinit var mRlEmptyContainer: View
    private lateinit var mDefIv: ImageView
    private lateinit var mLoadingIv: ImageView

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs)
    }

    /**
     * 初始化
     */
    private fun init(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StateLayout)
        mBgColor = try {
            a.getColor(R.styleable.StateLayout_background_color, Color.TRANSPARENT)
        } finally {
            a.recycle()
        }
        inflate(context, R.layout.base_layout_state_view, this)
        mEmptyLayout = findViewById(R.id.fl_empty_layout)
        mRlEmptyContainer = findViewById(R.id.rl_empty_container)
        mTvEmptyMessage = findViewById(R.id.tv_no_data)
        mLoadingIv = findViewById(R.id.vLoadingIv)
        mDefIv = findViewById(R.id.vDefIv)
        mEmptyLayout.setBackgroundColor(mBgColor)
        _switchEmptyView()
        mTvEmptyMessage.setOnClickListener { v: View ->
            mOnRetryListener?.onRetry()
        }
    }

    /**
     * 设置背景颜色
     * @param color
     */
    fun setBgColor(@ColorInt color: Int) {
        mEmptyLayout.setBackgroundColor(color)
    }

    /**
     * 隐藏视图
     */
    fun hide() {
        mEmptyStatus = STATUS_HIDE
        _switchEmptyView()
    }

    /**
     * 显示视图
     */
    fun show() {
        mEmptyStatus = STATUS_SHOW
        _switchEmptyView()
    }

    /**
     *  获取状态/设置状态
     */
    var emptyStatus: Int
        get() = mEmptyStatus
        set(emptyStatus) {
            mEmptyStatus = emptyStatus
            _switchEmptyView()
        }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    fun setStateMessage(msg: String?) {
        this.msg = msg
        mTvEmptyMessage.text = msg
    }

    /**
     * 设置图片
     *
     * @param resid
     */
    fun setStateIcon(@DrawableRes resid: Int) {
        mDefIv.setImageDrawable(ContextCompat.getDrawable(context, resid))
    }

    /**
     * 切换视图
     */
    private fun _switchEmptyView() {
        when (mEmptyStatus) {
            STATUS_LOADING -> {
                visibility = VISIBLE
                mRlEmptyContainer.visibility = GONE
                mLoadingIv.visibility = VISIBLE
            }
            STATUS_NO_DATA -> {
                visibility = VISIBLE
                setStateMessage(context.resources.getString(R.string.base_no_data))
                setStateIcon(R.drawable.base_ic_default_empty)
                mLoadingIv.visibility = GONE
                mRlEmptyContainer.visibility = VISIBLE
            }
            STATUS_ERROR -> {
                visibility = VISIBLE
                setStateMessage(context.resources.getString(R.string.base_data_error))
                setStateIcon(R.drawable.base_ic_default_error)
                mLoadingIv.visibility = GONE
                mRlEmptyContainer.visibility = VISIBLE
            }
            STATUS_HIDE -> visibility = GONE
            STATUS_SHOW -> visibility = VISIBLE
        }
    }

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    fun setRetryListener(retryListener: OnRetryListener?) {
        mOnRetryListener = retryListener
    }

    /**
     * 点击重试监听器
     */
    interface OnRetryListener {
        fun onRetry()
    }

    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    @IntDef(STATUS_LOADING, STATUS_ERROR, STATUS_NO_DATA)
    annotation class EmptyStatus
    companion object {
        const val STATUS_HIDE = 1001
        const val STATUS_SHOW = 1002
        const val STATUS_LOADING = 1
        const val STATUS_ERROR = 2
        const val STATUS_NO_DATA = 3
    }
}