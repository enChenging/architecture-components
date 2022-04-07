package com.release.architecture.common.net.helper

import com.release.architecture.base.utils.showToast
import com.release.architecture.common.net.helper.ResponseCodeEnum as ExceptionType

/**
 * 响应code异常统一处理
 *
 * 该方法主要做两件事:
 *
 * - 1.做统一的code码处理
 * - 2.未进行统一处理的code码会被转换为自定义异常[ResponseException]抛出
 *
 * 使用方式为：进行统一处理的异常进行抛出[ResponseEmptyException]，未进行处理的code抛出[ResponseException]
 *
 * @param code Int code码
 * @param msg String? 错误信息
 * @param successBlock suspend () -> Unit 没有异常的情况下执行的方法体 可以在此处进行数据的发射
 * @throws ResponseException 未进行处理的异常会进行抛出，让ViewModel去做进一步处理
 */
@JvmOverloads
@Throws(ResponseException::class)
suspend fun responseCodeExceptionHandler(
    code: Int,
    msg: String?,
    successBlock: suspend () -> Unit = {}
) {
    // 进行异常的处理
    when (code) {
        // 100-处理失败
        ExceptionType.ERROR.getCode() -> {
            // 先弹Toast 再抛异常
            showToast(msg ?: ExceptionType.ERROR.getMessage())
            throw ResponseException(ExceptionType.ERROR, msg ?: "")
        }
        // 200-成功
        ExceptionType.SUCCESS.getCode() -> successBlock.invoke()
        // 0-成功
        ExceptionType.SUCCESS2.getCode() -> successBlock.invoke()
        else -> throw ResponseException(ExceptionType.ERROR, msg ?: "")
    }
}