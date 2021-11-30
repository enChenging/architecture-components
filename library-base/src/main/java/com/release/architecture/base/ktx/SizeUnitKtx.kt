package com.release.architecture.base.ktx

import android.content.res.Resources

/**
 * 尺寸单位换算相关扩展属性
 *
 * @author yancheng
 * @since 2021/11/15
 */

private val displayMetrics = Resources.getSystem().displayMetrics

/**
 * dp 转 px
 */

val Float.dp2px: Int
    get() {
        return (this * (displayMetrics.density) + 0.5f).toInt()
    }

/**
 * px 转 dp
 */
val Float.px2dp: Int
    get() {
        return (this / (displayMetrics.density) + 0.5f).toInt()
    }

/**
 * sp 转 px
 */
val Float.sp2px: Int
    get() {
        return (this * (displayMetrics.scaledDensity) + 0.5f).toInt()
    }


/**
 * px 转 sp
 */
val Float.px2sp: Int
    get() {
        return (this / (displayMetrics.scaledDensity) + 0.5f).toInt()
    }

