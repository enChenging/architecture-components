package com.release.architecture.common.net.helper

/**
 * 自定义响应异常的抽象类型
 *
 */
interface IResponseException

/**
 * 请求响应异常，主要为各种code码专门定义的异常
 *
 * @param type IResponseCode 异常类型枚举，用于标记该异常的类型
 * @param msg String 异常信息
 *
 */
class ResponseException(val type: IResponseCode, val msg: String) : Exception(), IResponseException

/**
 * 空异常，表示该异常已经被处理过了，不需要再做额外处理了
 *
 */
class ResponseEmptyException : Exception(), IResponseException