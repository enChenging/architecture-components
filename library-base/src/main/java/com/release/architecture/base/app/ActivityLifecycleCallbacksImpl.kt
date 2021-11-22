package com.release.architecture.base.app

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.release.architecture.base.utils.logD

/**
 * Activity生命周期监听
 *
 * @author yancheng
 * @since 2021/11/15
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

    companion object {
        const val TAG = "ActivityLifecycle"
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
        ActivityStackManager.addActivityToStack(activity)
        logD("${activity.javaClass.simpleName} --> onActivityCreated", TAG)
    }

    override fun onActivityStarted(activity: Activity) {
        logD("${activity.javaClass.simpleName} --> onActivityStarted", TAG)
    }

    override fun onActivityResumed(activity: Activity) {
        logD("${activity.javaClass.simpleName} --> onActivityResumed", TAG)
    }

    override fun onActivityPaused(activity: Activity) {
        logD("${activity.javaClass.simpleName} --> onActivityPaused", TAG)
    }

    override fun onActivityStopped(activity: Activity) {
        logD("${activity.javaClass.simpleName} --> onActivityStopped", TAG)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        logD("${activity.javaClass.simpleName} --> onActivitySaveInstanceState", TAG)
    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityStackManager.popActivityToStack(activity)
        logD("${activity.javaClass.simpleName} --> onActivityDestroyed", TAG)
    }
}