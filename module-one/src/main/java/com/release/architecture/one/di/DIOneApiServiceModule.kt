package com.release.architecture.one.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.release.architecture.one.net.OneApiService
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *
 * One接口代理类依赖提供模块
 *
 * @author yancheng
 * @since 2021/11/15
 */
@Module
@InstallIn(SingletonComponent::class)
class DIOneApiServiceModule {

    /**
     * Example接口 Service 单例依赖提供方法
     *
     * @param retrofit Retrofit retrofit
     * @return OneApiService
     */
    @Singleton
    @Provides
    fun provideCommApiService(retrofit: Retrofit): OneApiService {
        return retrofit.create(OneApiService::class.java)
    }
}