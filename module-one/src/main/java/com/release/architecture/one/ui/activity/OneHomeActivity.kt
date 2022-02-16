package com.release.architecture.one.ui.activity

import android.annotation.SuppressLint
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.release.architecture.base.ktx.observeLiveData
import com.release.architecture.comm.base.BaseActivity
import com.release.architecture.comm.constant.RouteKey
import com.release.architecture.comm.constant.RouteUrl
import com.release.architecture.one.adapter.ArticleAdapter
import com.release.architecture.one.bean.ArticleBean
import com.release.architecture.one.databinding.OneActivityHomeBinding
import com.release.architecture.one.ui.vm.OneViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * OneActivity
 *
 * @author yancheng
 * @since 2021/11/15
 */
@AndroidEntryPoint
@Route(path = RouteUrl.One.OneHomeActivity)
class OneHomeActivity : BaseActivity<OneActivityHomeBinding, OneViewModel>() {

    override val mViewModel: OneViewModel by viewModels()

    @Inject
    lateinit var mArticleAdapter: ArticleAdapter

    override fun OneActivityHomeBinding.initView() {
        // 文章适配器
        mArticleAdapter.apply {
            data = mViewModel.mArticleList
            setOnItemClickListener { adapter, view, position ->
                val data = adapter.getItem(position) as ArticleBean
                ARouter.getInstance()
                    .build(RouteUrl.Two.TwoHomeActivity)
                    .withString(RouteKey.KEY_TITLE, data.title)
                    .withString(RouteKey.KEY_URL, data.link)
                    .navigation()
            }
        }
        vArticleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mArticleAdapter
        }
        vRefresh.run {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mViewModel.getArticleData()
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    mViewModel.getArticleData(isRefresh = false)
                }
            })
        }
    }

    override fun initObserve() {
        observeLiveData(mViewModel.mArticleLoadLD, ::processArticleData)
    }

    override fun initRequestData() {
        mViewModel.getArticleData()
    }

    /**
     * 处理文章数据
     * @param data List<ArticleBean>
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun processArticleData(data: List<ArticleBean>) {
        if (mViewModel.mCurPage == 0)
            mBinding.vRefresh.finishRefresh(500)
        else
            mBinding.vRefresh.finishLoadMore(500)
        mArticleAdapter.notifyDataSetChanged()
    }
}