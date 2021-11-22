package com.release.architecture.comm.net.helper

/**
 * 请求响应code枚举抽象
 *
 */
interface IResponseCode {

    /**
     * 获取该枚举的code码
     * @return Int
     */
    fun getCode(): Int

    /**
     * 获取该枚举的描述
     * @return String
     */
    fun getMessage(): String
}

/**
 * 请求响应code的枚举
 *
 */
enum class ResponseCodeEnum : IResponseCode {
    //登录超时
    TOKEN_INVALID {
        override fun getCode() = 403
        override fun getMessage() = "暂未登录或token已经过期"
    },

    //参数错误
    PARAMETER_ERROR {
        override fun getCode() = 102
        override fun getMessage() = "参数错误"
    },

    //用户不存在
    ERROR_NO_USER {
        override fun getCode() = 400
        override fun getMessage() = "用户不存在"
    },

    // 通用异常
    ERROR {
        override fun getCode() = 100
        override fun getMessage() = "处理失败"
    },

    // 登录成功
    SUCCESS {
        override fun getCode() = 200
        override fun getMessage() = "成功"
    },

    // 请求接口成功
    SUCCESS2 {
        override fun getCode() = 0
        override fun getMessage() = "成功"
    }
}