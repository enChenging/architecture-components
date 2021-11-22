package com.release.architecture.example.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.release.architecture.example.net.ExampleApiService
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 *
 * Example接口代理类依赖提供模块
 *
 * @author yancheng
 * @since 2021/11/15
 */
@Module
@InstallIn(SingletonComponent::class)
class DIExampleApiServiceModule {

    /**
     * Example接口 Service 单例依赖提供方法
     *
     * @param retrofit Retrofit retrofit
     * @return OneApiService
     */
    @Singleton
    @Provides
    fun provideCommApiService(retrofit: Retrofit): ExampleApiService {
        return retrofit.create(ExampleApiService::class.java)
    }
}