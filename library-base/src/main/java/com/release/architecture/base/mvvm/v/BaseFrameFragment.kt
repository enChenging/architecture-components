package com.release.architecture.base.mvvm.v

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.release.architecture.base.R
import com.release.architecture.base.ktx.observeStateLayout
import com.release.architecture.base.mvvm.vm.BaseViewModel
import com.release.architecture.base.utils.BindingReflex
import com.release.architecture.base.utils.EventBusRegister
import com.release.architecture.base.utils.EventBusUtils
import com.release.architecture.base.widget.StateLayout

/**
 * Fragment基类 与业务无关
 *
 * @author yancheng
 * @since 2021/11/15
 */
abstract class BaseFrameFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), FrameView<VB> {

    /**
     * 私有的 ViewBinding 此写法来自 Google Android 官方
     */
    private var _binding: VB? = null

    protected val mBinding get() = _binding!!

    protected abstract val mViewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BindingReflex.reflexViewBinding(javaClass, layoutInflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ARouter 依赖注入
        ARouter.getInstance().inject(this)
        // 注册EventBus
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.register(this)

        _binding?.initView()
        initStateLayout(view)
        initObserve()
        initRequestData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        if (javaClass.isAnnotationPresent(EventBusRegister::class.java)) EventBusUtils.unRegister(
            this
        )
        super.onDestroy()
    }

    /**
     * 初始化状态布局
     */
    open fun initStateLayout(view: View) {
        view.findViewById<StateLayout?>(R.id.vStateLayout)?.run {
            observeStateLayout(mViewModel.stateViewLD, this)
        }
    }
}