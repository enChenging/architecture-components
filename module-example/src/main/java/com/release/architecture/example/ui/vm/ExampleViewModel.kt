package com.release.architecture.example.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.release.architecture.base.mvvm.vm.BaseViewModel
import com.release.architecture.base.utils.logE
import com.release.architecture.base.utils.logI
import com.release.architecture.example.bean.ArticleBean
import com.release.architecture.example.ui.repo.ExampleRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Example VM层
 *
 * @author yancheng
 * @since 2021/11/15
 */
@HiltViewModel
class ExampleViewModel @Inject constructor(private val mRepo: ExampleRepo) : BaseViewModel() {
    /**
     * 当前页码
     */
    var mCurPage = 0

    /**
     * 文章列表数据源
     */
    val mArticleList = mutableListOf<ArticleBean>()

    /**
     * 文章列表数据加载
     */
    val mArticleLoadLD = MutableLiveData<List<ArticleBean>>()

    /**
     * 获取文章数据
     */
    fun getArticleData(isRefresh: Boolean = true) {
        if (isRefresh) {
            mCurPage = 0
            mArticleList.clear()
        } else
            mCurPage += 1
        logI("mCurPage == $mCurPage")
        viewModelScope.launch(Dispatchers.IO) {
            mRepo.getArticleData(mCurPage)
                .catch {
                    logE(it.message ?: "")
                }
                .onStart { changeStateView(loading = true) }
                .onCompletion { changeStateView(hide = true) }
                .collect {
                    mArticleList.addAll(it.articleList)
                    mArticleLoadLD.postValue(mArticleList)
                }
        }
    }
}