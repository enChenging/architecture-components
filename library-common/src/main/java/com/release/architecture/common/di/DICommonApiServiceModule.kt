package com.release.architecture.common.di

import com.release.architecture.base.ktx.create
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.release.architecture.common.net.CommonApiService
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 公共接口代理类依赖提供模块
 *
 * @author yancheng
 * @since 2021/11/15
 */
@Module
@InstallIn(SingletonComponent::class)
class DICommonApiServiceModule {

    /**
     * 公共接口 Service 单例依赖提供方法
     *
     * @param retrofit Retrofit retrofit
     * @return CommonApiService
     */
    @Singleton
    @Provides
    fun provideCommonApiService(retrofit: Retrofit): CommonApiService {
        return create(retrofit)
    }
}