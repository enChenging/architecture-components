package com.release.architecture.one.ui.repo

import com.release.architecture.base.mvvm.m.BaseRepository
import com.release.architecture.comm.net.helper.responseCodeExceptionHandler
import com.release.architecture.one.bean.ArticlePageBean
import com.release.architecture.one.net.OneApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * One 仓库层
 *
 * @author yancheng
 * @since 2021/11/15
 */
class OneRepo @Inject constructor(private val mAPi: OneApiService) : BaseRepository() {
    @Inject
    lateinit var mApi: OneApiService

    /**
     * 获取文章数据
     * @param page Int 分页加载的页码 从0开始
     * @return Flow<ArticlePageBean>
     */
    suspend fun getArticleData(page: Int) =
        request<ArticlePageBean> {
            // 如果页码为0 需要同步进行获取置顶文章进行合并
            if (page == 0) {
                val article: ArticlePageBean
                withContext(Dispatchers.IO) {
                    // 开启 async 请求置顶文章
                    val topArticleJob = async(Dispatchers.IO) {
                        mApi.getTopArticle().run {
                            responseCodeExceptionHandler(code, msg)
                            data
                        }
                    }
                    // 开启 async 请求文章
                    val articleJob = async(Dispatchers.IO) {
                        mApi.getArticleByPage(page).run {
                            responseCodeExceptionHandler(code, msg)
                            data
                        }
                    }
                    // 合并两个请求的结果
                    val topArticleList = topArticleJob.await()
                    topArticleList.forEach { it.top = true }
                    article = articleJob.await()
                    topArticleList.addAll(article.articleList)
                    article.articleList.clear()
                    article.articleList.addAll(topArticleList)
                }
                emit(article)
            } else {
                mApi.getArticleByPage(page).run {
                    responseCodeExceptionHandler(code, msg) {
                        // 发送数据
                        emit(data)
                    }
                }
            }
        }

}