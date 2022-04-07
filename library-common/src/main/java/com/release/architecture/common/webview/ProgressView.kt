package com.release.architecture.common.webview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.view.ViewCompat

/**
 * 进度条
 *
 * @author yancheng
 * @since 2021/7/29
 */
class ProgressView : View {

    //设置进度条颜色
    private var defaultColor = -0x497d8
    private var currentProgress = 0
    private var totalProgress = 0
    private var isHide = false
    private var animator: ValueAnimator? = null
    private var viewWidth = 0
    private var viewHeight = 0
    private val progressPaint = Paint().apply {
        color = defaultColor
    }
    private var progressCircle: Paint = Paint().apply {
        color = defaultColor
        //模糊效果
        maskFilter = BlurMaskFilter(10f, BlurMaskFilter.Blur.SOLID)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        //关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null)
    }

    /**
     * 测量
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        viewWidth = measuredWidth - paddingLeft - paddingRight
        viewHeight = measuredHeight
    }

    /**
     * 绘制进度条
     * @param canvas
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (currentProgress <= 100 && isHide) {
            isHide = false
            this.alpha = 1f
        }
        canvas.drawRect(
            0f,
            0f,
            (viewWidth * (currentProgress / 100.0)).toFloat(),
            viewHeight.toFloat(),
            progressPaint
        )
        canvas.drawCircle(
            (viewWidth * (currentProgress / 100.0)).toFloat() - viewHeight / 2,
            (viewHeight / 2).toFloat(),
            viewHeight.toFloat(),
            progressCircle
        )
        if (currentProgress >= 100) {
            hideSelf()
        }
    }

    /**
     * 隐藏进度条
     */
    private fun hideSelf() {
        postDelayed({
            ViewCompat.animate(this@ProgressView).alpha(0f)
            isHide = true
            currentProgress = 0
        }, 100)
    }

    /**
     * 设置进度
     * @param progress
     */
    fun setProgress(progress: Int) {
        totalProgress = progress
        if (animator != null) {
            if (animator?.isRunning == true) {
                animator?.cancel()
            }
        }
        animator = ValueAnimator.ofInt(currentProgress, totalProgress)
        animator?.duration = 300
        animator?.addUpdateListener { animation ->
            currentProgress = animation.animatedValue as Int
            invalidate()
        }
        animator?.start()
    }

    /**
     * 设置进度颜色
     * @param color Int
     */
    fun setDefaultColor(color: Int) {
        progressPaint.color = color
        progressCircle.color = color
    }
}