package com.release.architecture.two.ui

//import com.release.architecture.push.XXX
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.release.architecture.base.mvvm.vm.EmptyViewModel
import com.release.architecture.base.utils.SpannableStringUtils
import com.release.architecture.common.base.BaseActivity
import com.release.architecture.common.constant.RouteKey
import com.release.architecture.common.constant.RouteUrl
import com.release.architecture.common.ui.CommonWebActivity
import com.release.architecture.two.databinding.TwoActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * Two首页
 *
 * @author yancheng
 * @since 2021/11/15
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Two.TwoHomeActivity)
class TwoHomeActivity : BaseActivity<TwoActivityHomeBinding, EmptyViewModel>() {

    /**
     * 跳转参数(非必传) 标题
     */
    @JvmField
    @Autowired(name = RouteKey.KEY_TITLE)
    var mTitle: String = "百度"

    /**
     * 跳转参数(非必传) 网址
     */
    @JvmField
    @Autowired(name = RouteKey.KEY_URL)
    var mUrl: String = "https://www.baidu.com"

    override val mViewModel: EmptyViewModel by viewModels()

    override fun TwoActivityHomeBinding.initView() {
        vJumpWebBtn.setOnClickListener {
            CommonWebActivity.start(mTitle, mUrl)
        }
        SpannableStringUtils.with(vJumpWebBtn)
            .append("点击跳转到<<")
            .setForegroundColor(
                ContextCompat.getColor(
                    this@TwoHomeActivity,
                    android.R.color.holo_blue_light
                )
            )
            .append(mTitle)
            .setForegroundColor(
                ContextCompat.getColor(
                    this@TwoHomeActivity,
                    android.R.color.holo_red_light
                )
            )
            .append(">>详情")
            .setForegroundColor(
                ContextCompat.getColor(
                    this@TwoHomeActivity,
                    android.R.color.holo_blue_light
                )
            )
            .create()
    }

    override fun initObserve() {}

    override fun initRequestData() {}
}