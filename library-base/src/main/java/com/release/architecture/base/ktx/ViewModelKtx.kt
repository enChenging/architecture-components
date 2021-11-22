package com.release.architecture.base.ktx

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

/**
 * 开启一个线程调度模式为[Dispatchers.IO]的协程 有默认的异常处理器
 *
 * @receiver ViewModel
 *
 * @param exceptionHandler CoroutineExceptionHandler 异常处理器
 * @param block suspend CoroutineScope.() -> Unit 协程体
 * @return Job
 *
 * @author yancheng
 * @since 2021/11/15
 */
fun ViewModel.launchIO(
    exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    },
    block: suspend CoroutineScope.() -> Unit
): Job = viewModelScope.launch(Dispatchers.IO + exceptionHandler, block = block)