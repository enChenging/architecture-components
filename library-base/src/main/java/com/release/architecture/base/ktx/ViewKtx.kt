package com.release.architecture.base.ktx

import android.animation.Animator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import com.chad.library.adapter.base.BaseQuickAdapter
import com.release.architecture.base.ktx.ViewClickDelay.SPACE_TIME
import com.release.architecture.base.ktx.ViewClickDelay.hash
import com.release.architecture.base.ktx.ViewClickDelay.lastClickTime

/**
 * View相关的扩展方法
 *
 * @author yancheng
 * @since 2021/11/15
 */

/*************************************** View可见性相关 ********************************************/

/**
 * 判断View是不是[View.VISIBLE]状态
 */
val View.isVisible: Boolean
    get() {
        return visibility == View.VISIBLE
    }

/**
 * 判断View是不是[View.INVISIBLE]状态
 */
val View.isInvisible: Boolean
    get() {
        return visibility == View.INVISIBLE
    }

/**
 * 判断View是不是[View.GONE]状态
 */
val View.isGone: Boolean
    get() {
        return visibility == View.GONE
    }

/**
 * 隐藏View
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * 显示View
 * @receiver View
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * View不可见但存在原位置
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}
/*************************************** View宽高相关 ********************************************/

/**
 * 将该View的高度设置为[height]
 * @receiver View
 * @param height Int 需要设置的高度
 * @return View 此View
 */
fun View.height(height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.height = height
    layoutParams = params
    return this
}

/**
 * 将该View的宽度设置为[width]
 * @receiver View
 * @param width Int 需要设置的宽度
 * @return View 此View
 */
fun View.width(width: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.width = width
    layoutParams = params
    return this
}

/**
 * 将View的宽高设置为传入的[width]、[height]
 * @receiver View
 * @param width Int 要设置的宽度
 * @param height Int 要设置的高度
 * @return View 此View
 */
fun View.widthAndHeight(width: Int, height: Int): View {
    val params = layoutParams ?: ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )
    params.width = width
    params.height = height
    layoutParams = params
    return this
}

/**
 * 将此View的宽度设置为[width]，并且带有过渡动画
 * @receiver View
 * @param width Int 需要设置的宽度
 * @param duration Long 动画时长
 * @param listener AnimatorListener? 动画监听
 * @param updateCall (v:Float) -> Unit 动画更新回调
 * @return ValueAnimator?
 */
inline fun View.animateWidth(
    width: Int,
    duration: Long = 400L,
    listener: Animator.AnimatorListener? = null,
    crossinline updateCall: (v: Float) -> Unit = { }
): ValueAnimator? {
    var animator: ValueAnimator? = null
    post {
        animator = ValueAnimator.ofInt(width, width).apply {
            addUpdateListener {
                width(it.animatedValue as Int)
                updateCall.invoke(it.animatedFraction)
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
    return animator
}

/**
 * 将此View的高度设置为[height]，并且带有过渡动画
 * @receiver View
 * @param height Int 需要设置的高度
 * @param duration Long 动画时长
 * @param listener AnimatorListener? 动画监听
 * @param updateCall (v:Float) -> Unit 动画更新回调
 * @return ValueAnimator?
 */
inline fun View.animateHeight(
    height: Int,
    duration: Long = 400,
    listener: Animator.AnimatorListener? = null,
    crossinline updateCall: (v: Float) -> Unit = {}
): ValueAnimator? {
    var animator: ValueAnimator? = null
    post {
        animator = ValueAnimator.ofInt(height, height).apply {
            addUpdateListener {
                height(it.animatedValue as Int)
                updateCall.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
    return animator
}

/**
 * 将此View的宽高设置为[targetWidth]、[targetHeight]，并且带有过渡动画
 * @receiver View
 * @param targetWidth Int 需要设置的宽度
 * @param targetHeight Int 需要设置的高度
 * @param duration Long 动画时长
 * @param listener AnimatorListener? 动画监听
 * @param updateCall (v:Float) -> Unit 动画更新回调
 * @return ValueAnimator?
 */
inline fun View.animateWidthAndHeight(
    targetWidth: Int,
    targetHeight: Int,
    duration: Long = 400,
    listener: Animator.AnimatorListener? = null,
    crossinline updateCall: ((Float) -> Unit) = {}
): ValueAnimator? {
    var animator: ValueAnimator? = null
    post {
        val startHeight = height
        val evaluator = IntEvaluator()
        animator = ValueAnimator.ofInt(width, targetWidth).apply {
            addUpdateListener {
                widthAndHeight(
                    it.animatedValue as Int,
                    evaluator.evaluate(it.animatedFraction, startHeight, targetHeight)
                )
                updateCall.invoke((it.animatedFraction))
            }
            if (listener != null) addListener(listener)
            setDuration(duration)
            start()
        }
    }
    return animator
}
/*************************************** View自定义背景 ********************************************/

/**
 * 快捷设置View的自定义纯色带圆角背景
 * @receiver View
 * @param color Int 颜色值
 * @param cornerRadius Float 圆角 单位px
 */
fun View.setRoundRectBg(
    @ColorInt color: Int,
    cornerRadius: Float = 15F
) {
    background = GradientDrawable().apply {
        setColor(color)
        setCornerRadius(cornerRadius)
    }
}

/*************************************** View其他 ********************************************/
/**
 * 获取View id，如果没有id：SDK>17, 使用[View.generateViewId]
 */
fun View.getViewId(): Int {
    var id = id
    if (id == View.NO_ID) {
        id = View.generateViewId()
    }
    return id
}

object ViewClickDelay {
    var hash: Int = 0
    var lastClickTime: Long = 0
    var SPACE_TIME: Long = 2000  // 间隔时间
}

/**
 * 防快速点击
 * @receiver View
 * @param clickAction 要响应的操作
 */
infix fun View.clickDelay(clickAction: () -> Unit) {
    this.setOnClickListener {
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction()
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > SPACE_TIME) {
                lastClickTime = System.currentTimeMillis()
                clickAction()
            }
        }
    }
}

/**
 * 防快速点击
 * @receiver View
 * @param clickAction Function3<[@kotlin.ParameterName] View, [@kotlin.ParameterName] Int, [@kotlin.ParameterName] KeyEvent, Unit>
 */
infix fun View.clickOnKeyDelay(clickAction: (v:View, keyCode:Int, event: KeyEvent) -> Unit) {
    this.setOnKeyListener { v, keyCode, event ->
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction(v,keyCode,event)
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > SPACE_TIME) {
                lastClickTime = System.currentTimeMillis()
                clickAction(v,keyCode,event)
            }
        }
        false
    }
}

/**
 * BaseQuickAdapter
 * 防快速点击
 */
infix fun BaseQuickAdapter<*, *>.itemClickDelay(clickAction: (adapter: BaseQuickAdapter<*, *>, view: View, pos: Int) -> Unit) {
    this.setOnItemClickListener { adapter, view, position ->
        if (this.hashCode() != hash) {
            hash = this.hashCode()
            lastClickTime = System.currentTimeMillis()
            clickAction(adapter, view, position)
        } else {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > SPACE_TIME) {
                lastClickTime = System.currentTimeMillis()
                clickAction(adapter, view, position)
            }
        }
    }
}