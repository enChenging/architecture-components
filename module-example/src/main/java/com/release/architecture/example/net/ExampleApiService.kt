package com.release.architecture.example.net

import com.release.architecture.comm.base.BaseResponse
import com.release.architecture.example.bean.ArticleBean
import com.release.architecture.example.bean.ArticlePageBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 * Example模块数据接口
 *
 * @author yancheng
 * @since 2021/11/15
 */
interface ExampleApiService {
    /**
     * 获取置顶文章
     * @return BaseResponse<MutableList<ArticleBean>> 置顶文章列表
     */
    @GET("article/top/json")
    suspend fun getTopArticle(): BaseResponse<MutableList<ArticleBean>>

    /**
     * 根据页面获取分页的文章数据
     * @param page Int 获取的文章页码 从 0 开始
     * @return BaseResponse<ArticlePageBean>
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleByPage(@Path("page") page: Int): BaseResponse<ArticlePageBean>


}