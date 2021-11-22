package com.release.architecture.comm.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.release.architecture.comm.net.CommApiService
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
class DICommApiServiceModule {

    /**
     * 公共接口 Service 单例依赖提供方法
     *
     * @param retrofit Retrofit retrofit
     * @return CommApiService
     */
    @Singleton
    @Provides
    fun provideCommApiService(retrofit: Retrofit): CommApiService {
        return retrofit.create(CommApiService::class.java)
    }
}