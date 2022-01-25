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





fun ViewModel.launchOnUI(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch {
        block()
    }
}

suspend fun ViewModel.launchOnIO(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.IO) {
        block()
    }
}


fun ViewModel.launch(tryBlock: suspend CoroutineScope.() -> Unit) {
    launchOnUI {
        tryCatch(tryBlock, {}, {}, true)
    }
}

fun ViewModel.launchOnUITryCatch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit
) {
    launchOnUI {
        tryCatch(tryBlock, catchBlock, {}, true)
    }
}

suspend fun ViewModel.launchOnIOTryCatch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit
) {
    launchOnIO {
        tryCatch(tryBlock, catchBlock, {}, true)
    }
}

fun ViewModel.launchOnUITryCatch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
    finallyBlock: suspend CoroutineScope.() -> Unit,
    handleCancellationExceptionManually: Boolean
) {
    launchOnUI {
        tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
    }
}

fun ViewModel.launchOnUITryCatch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    handleCancellationExceptionManually: Boolean = false
) {
    launchOnUI {
        tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
    }
}


private suspend fun ViewModel.tryCatch(
    tryBlock: suspend CoroutineScope.() -> Unit,
    catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
    finallyBlock: suspend CoroutineScope.() -> Unit,
    handleCancellationExceptionManually: Boolean = false
) {
    coroutineScope {
        try {
            tryBlock()
        } catch (e: Throwable) {
            if (e !is CancellationException || handleCancellationExceptionManually) {
                catchBlock(e)
            } else {
                throw e
            }
        } finally {
            finallyBlock()
        }
    }
}