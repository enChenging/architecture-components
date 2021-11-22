package com.release.architecture.base.ktx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.release.architecture.base.utils.StateLayoutEnum
import com.release.architecture.base.widget.StateLayout

/**
 * 对LiveData订阅的简化封装
 *
 * 使用示例
 * ```
 *  override fun initObserve() {
 *      observeLiveData(mViewModel.stateViewLD, ::stateViewLivaDataHandler)
 *  }
 *
 *  private fun stateViewLivaDataHandler(data: StateLayoutEnum) {
 *      ...
 *  }
 * ```
 *
 * @receiver LifecycleOwner
 * @param liveData LiveData<T> 需要进行订阅的LiveData
 * @param action action: (t: T) -> Unit 处理订阅内容的方法
 * @return Unit
 *
 * @author yancheng
 * @since 2021/11/15
 */
fun <T> LifecycleOwner.observeLiveData(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, {
        it?.let { t -> action(t) }
    })
}

fun <T> LifecycleOwner.observeLiveData2(liveData: LiveData<T>, action: (t: T?) -> Unit) {
    liveData.observe(this, { action(it) })
}
/**
 * 对状态视图绑定的扩展
 *
 * 使用示例
 * ```
 *   findViewById<StateLayout?>(R.id.vStateLayout)?.run {
 *       observeStateLayout(mViewModel.stateViewLD, this)
 *   }
 * ```
 *
 * @receiver LifecycleOwner
 * @param liveData MutableLiveData<StateLayoutEnum> 状态LiveData
 * @param stateLayout StateLayout 状态布局
 * @return Unit
 */
fun LifecycleOwner.observeStateLayout(
    liveData: MutableLiveData<StateLayoutEnum>,
    stateLayout: StateLayout
) {
    liveData.observe(this, {
        stateLayout.run {
            when (it) {
                StateLayoutEnum.LOADING -> emptyStatus = StateLayout.STATUS_LOADING
                StateLayoutEnum.NO_DATA -> emptyStatus = StateLayout.STATUS_NO_DATA
                StateLayoutEnum.HIDE -> hide()
                StateLayoutEnum.ERROR -> emptyStatus = StateLayout.STATUS_ERROR
                else -> {
                }
            }
        }
    })
}