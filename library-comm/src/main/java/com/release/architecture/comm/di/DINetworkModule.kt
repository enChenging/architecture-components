package com.release.architecture.comm.di

import com.release.architecture.base.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.release.architecture.base.constant.VersionStatus
import com.release.architecture.comm.constant.NetUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * 全局作用域的网络层的依赖注入模块
 *
 * @author yancheng
 * @since 2021/11/15
 */
@Module
@InstallIn(SingletonComponent::class)
class DINetworkModule {

    /**
     * [OkHttpClient]依赖提供方法
     *
     * @return OkHttpClient
     */
    @Singleton
    @Provides
    fun provideLocalOkHttpClient(): OkHttpClient {
        // 日志拦截器部分
        val level =
            if (BuildConfig.VERSION_TYPE != VersionStatus.RELEASE) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val logInterceptor = HttpLoggingInterceptor().setLevel(level)

        return OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .connectTimeout(30L * 1000L, TimeUnit.MILLISECONDS)
            .readTimeout(30L * 1000L, TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * [Retrofit]依赖提供方法
     *
     * @param okHttpClient OkHttpClient OkHttp客户端
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun provideLocalRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NetUrl.TEST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}