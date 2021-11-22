package com.release.architecture.base.utils

import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import com.alibaba.android.arouter.launcher.ARouter
import com.release.architecture.base.BaseApp
import com.release.architecture.base.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 以顶层函数存在的常用工具方法
 * startPolling() -> 开启一个轮询
 * toast() -> Toast
 * sendEvent() -> 发送普通EventBus事件
 * startActivityByRoute() -> 阿里路由不带参数跳转
 * getVersionCode() -> 获取App版本号
 * getVersionName() -> 获取App版本名
 *
 */
/**************************************************************************************************/
/**
 * 使用 Flow 做的简单的轮询
 * 请使用单独的协程来进行管理该 Flow
 * Flow 仍有一些操作符是实验性的 使用时需添加 @InternalCoroutinesApi 注解
 * @param intervals 轮询间隔时间/毫秒
 * @param block 需要执行的代码块
 */
suspend inline fun startPolling(intervals: Long, crossinline block: () -> Unit) {
    flow {
        while (true) {
            delay(intervals)
            emit(0)
        }
    }
        .catch { logE("startPolling: $it") }
        .flowOn(Dispatchers.Default)  // 指定 flow{}、catch{}的线程模型，collect{}的线程取决于父协程的线程模型
        .collect { block.invoke() }
}

/**
 * 开始立刻循环
 * @param intervals Long
 * @param block Function0<Unit>
 */
suspend inline fun startImmediatelyPolling(intervals: Long,crossinline  block: () -> Unit) {
    flow {
        while (true) {
            emit(0)
            delay(intervals)
        }
    }
        .catch { logE("startPolling: $it") }
        .flowOn(Dispatchers.Default)  // 指定 flow{}、catch{}的线程模型，collect{}的线程取决于父协程的线程模型
        .collect { block.invoke() }
}
/**************************************************************************************************/

/**
 * 发送普通EventBus事件
 */
fun sendEvent(event: Any) = EventBusUtils.postEvent(event)

/**************************************************************************************************/
/**
 * 阿里路由不带参数跳转
 * @param routerUrl String 路由地址
 */
fun startActivityByRoute(routerUrl: String) {
    ARouter.getInstance().build(routerUrl).navigation()
}

/**************************************************************************************************/
/**
 * 获取App版本号
 * @return Long App版本号
 */
fun getVersionCode(): Long {
    val packageInfo = BaseApp.context
        .packageManager
        .getPackageInfo(BaseApp.context.packageName, 0)
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        packageInfo.longVersionCode
    } else {
        packageInfo.versionCode.toLong()
    }
}

/**************************************************************************************************/
/**
 * 获取App版本名
 * @return String App版本名
 */
fun getVersionName(): String {
    return BaseApp.context
        .packageManager
        .getPackageInfo(BaseApp.context.packageName, 0)
        .versionName
}
/**************************************************************************************************/

private var mToast: Toast? = null

/**
 * toast
 * @param text String
 */
fun showToast(text: String, durations: Int = Toast.LENGTH_SHORT) {
    BaseApp.mCoroutineScope.launch(Dispatchers.Main) {
        mToast?.cancel()
        mToast = Toast(BaseApp.context).apply {
            setGravity(Gravity.CENTER, 0, 0)
            duration = durations
            val layout =
                LayoutInflater.from(BaseApp.context).inflate(R.layout.base_toast, null)
            view = layout
            layout.findViewById<TextView>(R.id.vMsgTv)?.text = text
            show()
        }
    }
}

/**
 * 倒计时
 * @param total Int
 * @param onTick Function1<Int, Unit>
 * @param onFinish Function0<Unit>
 * @param scope LifecycleCoroutineScope
 * @return Job
 */
fun countDownCoroutines(total:Int,onTick:(Int)->Unit,onFinish:()->Unit,
                        scope: LifecycleCoroutineScope
): Job {
    return flow{
        for (i in total downTo 0){
            emit(i)
            delay(1000)
        }
    }.flowOn(Dispatchers.Default)
        .onCompletion { onFinish.invoke() }
        .onEach { onTick.invoke(it) }
        .flowOn(Dispatchers.Main)
        .launchIn(scope)
}