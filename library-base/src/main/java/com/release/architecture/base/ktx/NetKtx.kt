package com.release.architecture.base.ktx

import retrofit2.Retrofit

/**
 *
 * @author yancheng
 * @since 2021/11/25
 */
inline fun <reified T>create(retrofit: Retrofit):T {
    return retrofit.create(T::class.java)
}