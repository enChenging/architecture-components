package com.release.architecture.base.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.lang.reflect.Field

/**
 * 解决 Android 自身的 Bug
 *
 * @author yancheng
 * @since 2021/11/15
 */
class AndroidBugFixUtils {

    /**
     * 解决 InputMethodManager 造成的内存泄露
     */
    fun fixSoftInputLeaks(activity: Activity) {
        val imm =
            com.release.architecture.base.BaseApp.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val leakViews = arrayOf("mLastSrvView", "mCurRootView", "mServedView", "mNextServedView")
        for (leakView in leakViews) {
            try {
                val leakViewField: Field =
                    InputMethodManager::class.java.getDeclaredField(leakView) ?: continue
                if (!leakViewField.isAccessible) leakViewField.isAccessible = true
                val view: Any? = leakViewField.get(imm)
                if (view !is View) continue
                if (view.rootView == activity.window.decorView.rootView) {
                    leakViewField.set(imm, null)
                }
            } catch (t: Throwable) {
            }
        }
    }
}