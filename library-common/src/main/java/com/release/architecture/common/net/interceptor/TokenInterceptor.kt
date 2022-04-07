package com.release.architecture.common.net.interceptor

import com.release.architecture.common.net.helper.ResponseCodeEnum
import com.release.architecture.common.net.helper.TokenManager
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 *  自定义token拦截器
 * @author yancheng
 * @since 2021/7/9
 */
class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = TokenManager.getToken()
        val oldRequest = chain.request()
        // 校验token
        return if (checkTokenValidPeriod(oldRequest)) {
            val request = oldRequest.newBuilder()
                .addHeader("Authorization", token)
                .build()
            val response = chain.proceed(request)
//            val content = response.body?.string() ?: ""
//            logI("content == $content")
            response
        } else {
            // 失效返回新的Response
            getNewResponse(oldRequest)
        }
    }

    /**
     * 校验token有效期
     * @return Boolean 是否有效
     */
    private fun checkTokenValidPeriod(request: Request): Boolean {
        // 过滤掉不需要校验的接口
        return if (request.url.toString().contains("login")) {
            TokenManager.refreshTime(System.currentTimeMillis())
            true
        } else {
            val newTime = System.currentTimeMillis()
            val oldTime = TokenManager.getLastRequestTime()
            val difference = newTime - oldTime
            // 判断时间差值是否超过7天
            if (difference > 604800000L) {
                false
            } else {
                // 刷新请求时间
                TokenManager.refreshTime(newTime)
                true
            }
        }
    }

    /**
     * 获取token失效后的新的响应
     * @param request Request
     * @return Response
     */
    private fun getNewResponse(request: Request): Response {
        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .body(
                "{\"code\": ${ResponseCodeEnum.TOKEN_INVALID.getCode()},\"msg\": \"token失效请重新登陆\",\"data\": null}"
                    .toResponseBody(null)
            )
            .message("token expired!!!")
            .build()
    }
}