package com.release.architecture.common.net.interceptor

import com.release.architecture.base.utils.logD
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSink
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

/**
 * 自定义请求头的拦截器
 * @author yancheng
 * @since 2021/7/5
 */
class LogInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val startTime = System.currentTimeMillis()
        val response = chain.proceed(chain.request())
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        val content = response.body?.string() ?: ""
        val mediaType = response.body?.contentType()

        val requestBuffer = Buffer()
        val requestBody = request.body

        if (requestBody != null)
            (requestBuffer as BufferedSink).let { requestBody.writeTo(it) }

        //打印url信息
        val url: String = request.url.toString() +
                if (requestBody != null) "?" + parseParams(requestBody, requestBuffer) else ""
        logD("$url \n 请求用时$duration 毫秒 \n $content")
        return response.newBuilder().body(ResponseBody.create(mediaType, content)).build()
    }


    /**
     * 解析返回请求的参数
     * @param body RequestBody 请求body
     * @param requestBuffer Buffer 将请求数据放到Buffer中打印
     * @return String
     * @throws UnsupportedEncodingException 不支持的编码格式(写错编码格式)
     */
    @Throws(UnsupportedEncodingException::class)
    private fun parseParams(body: RequestBody, requestBuffer: Buffer): String {
        var isHasMultipart = false
        if (body.contentType() != null) {
            isHasMultipart = body.contentType().toString().contains("multipart")
        }
        return if (body.contentType() != null && !isHasMultipart
        ) {
            URLDecoder.decode(requestBuffer.readUtf8(), "UTF-8")
        } else "null"
    }
}