package com.release.architecture.example2.ui

//import com.release.architecture.push.XXX
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.release.architecture.base.mvvm.vm.EmptyViewModel
import com.release.architecture.base.utils.SpannableStringUtils
import com.release.architecture.comm.base.BaseActivity
import com.release.architecture.comm.constant.RouteKey
import com.release.architecture.comm.constant.RouteUrl
import com.release.architecture.comm.ui.CommWebActivity
import com.release.architecture.example2.databinding.Example2ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 首页
 *
 * @author yancheng
 * @since 2021/11/15
 */
@AndroidEntryPoint
@Route(path = RouteUrl.Example2.Example2HomeActivity)
class Example2HomeActivity : BaseActivity<Example2ActivityHomeBinding, EmptyViewModel>() {

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

    override fun Example2ActivityHomeBinding.initView() {
        vJumpWebBtn.setOnClickListener {
            CommWebActivity.start(mTitle, mUrl)
        }
        SpannableStringUtils.with(vJumpWebBtn)
            .append("点击跳转到<<")
            .setForegroundColor(
                ContextCompat.getColor(
                    this@Example2HomeActivity,
                    android.R.color.holo_blue_light
                )
            )
            .append(mTitle)
            .setForegroundColor(
                ContextCompat.getColor(
                    this@Example2HomeActivity,
                    android.R.color.holo_red_light
                )
            )
            .append(">>详情")
            .setForegroundColor(
                ContextCompat.getColor(
                    this@Example2HomeActivity,
                    android.R.color.holo_blue_light
                )
            )
            .create()
    }

    override fun initObserve() {}

    override fun initRequestData() {}

}