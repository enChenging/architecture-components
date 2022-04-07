package com.release.architecture.common.repo

import com.release.architecture.base.mvvm.m.BaseRepository
import com.release.architecture.common.net.CommonApiService
import javax.inject.Inject

/**
 * 公共数据仓库
 * 写此类主要是为了复用代码
 *
 */
class CommonRepository @Inject constructor(private val mApi: CommonApiService) : BaseRepository()