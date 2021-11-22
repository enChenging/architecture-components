package com.release.architecture.comm.repo

import com.release.architecture.base.mvvm.m.BaseRepository
import com.release.architecture.comm.net.CommApiService
import javax.inject.Inject

/**
 * 公共数据仓库
 * 写此类主要是为了复用代码
 *
 */
class CommRepository @Inject constructor(private val mApi: CommApiService) : BaseRepository()