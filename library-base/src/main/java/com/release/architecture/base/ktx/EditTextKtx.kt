package com.release.architecture.base.ktx

import android.text.InputFilter
import android.widget.EditText

/**
 * EditText相关扩展方法
 *
 * @author yancheng
 * @since 2021/11/15
 */

/**
 * 过滤掉空格和回车
 */
fun EditText.filterBlankAndCarriageReturn() {
    val filterList = mutableListOf<InputFilter>()
    filterList.addAll(filters)
    filterList.add(InputFilter { source, _, _, _, _, _ -> if (source == " " || source == "\n") "" else null })
    filters = filterList.toTypedArray()
}