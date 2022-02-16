package com.release.architecture.debug

import androidx.activity.viewModels
import com.release.architecture.base.ktx.observeLiveData
import com.release.architecture.base.utils.SpUtils
import com.release.architecture.base.utils.logI
import com.release.architecture.base.utils.showToast
import com.release.architecture.base.utils.startActivityByRoute
import com.release.architecture.comm.base.BaseActivity
import com.release.architecture.comm.constant.RouteUrl
import com.release.architecture.comm.net.helper.TokenManager
import com.release.architecture.comm.proxy.PermissionRequest
import com.release.architecture.debug.databinding.DebugActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * 测试环境下的登陆界面
 *
 * @author yancheng
 * @since 2021/8/6
 */
@AndroidEntryPoint
class DebugLoginActivity : BaseActivity<DebugActivityLoginBinding, DebugLoginViewModel>() {

    private val mPermissionRequest by lazy(mode = LazyThreadSafetyMode.NONE) { PermissionRequest() }

    override val mViewModel: DebugLoginViewModel by viewModels()

    var mCurRouteUrl = RouteUrl.One.OneHomeActivity

    override fun DebugActivityLoginBinding.initView() {
    }

    override fun initObserve() {
        observeLiveData(mViewModel.loginLD, ::processLoginLD)
    }

    override fun initRequestData() {
        if (TokenManager.getToken().isNotBlank()) {
            SpUtils.getString("DEBUG_HOME_PAGE", mCurRouteUrl).run {
                startActivityByRoute(this)
                finish()
            }
        } else {
            mViewModel.login("111", "123456", "1", "")
        }
    }

    /**
     * 处理登陆LiveData
     * @param b Boolean 是否登陆成功
     * @return Unit
     */
    private fun processLoginLD(b: Boolean) {
        if (b) {
            // 权限获取
            mPermissionRequest.permissionRequest(this, {
                logI("packageName == $packageName")
                when {
                    packageName.contains(".one") -> {
                        mCurRouteUrl = RouteUrl.One.OneHomeActivity
                    }
                    packageName.contains(".two") -> {
                        mCurRouteUrl = RouteUrl.Two.TwoHomeActivity
                    }
                }
                startActivityByRoute(mCurRouteUrl)
                SpUtils.putString("DEBUG_HOME_PAGE", mCurRouteUrl)
                finish()
            }, {
                showToast("未授予全部权限")
            })
        } else {
            showToast("密码错误")
        }
    }
}
