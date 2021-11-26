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
fun Float.dp2px(): Int {
    val scale = displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

/**
 * px 转 dp
 */
fun Float.px2dp(): Int {
    val scale = displayMetrics.density
    return (this / scale + 0.5f).toInt()
}

/**
 * sp 转 px
 */
fun Float.sp2px(): Int {
    val scale = displayMetrics.scaledDensity
    return (this * scale + 0.5f).toInt()
}

/**
 * px 转 sp
 */
fun Float.px2sp(): Int {
    val scale = displayMetrics.scaledDensity
    return (this / scale + 0.5f).toInt()
}
