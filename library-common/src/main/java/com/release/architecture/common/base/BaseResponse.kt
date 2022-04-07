package com.release.architecture.common.base

/**
 * Response封装
 *
 * @author yancheng
 * @since 2021/11/15
 */
data class BaseResponse<D>(
    val code: Int,
    val `data`: D,
    val msg: String?
)