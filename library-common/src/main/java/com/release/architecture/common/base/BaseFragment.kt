package com.release.architecture.common.base

import androidx.viewbinding.ViewBinding
import com.release.architecture.base.mvvm.v.BaseFrameFragment
import com.release.architecture.base.mvvm.vm.BaseViewModel

/**
 * 项目相关的Fragment基类
 *
 * @author yancheng
 * @since 2021/11/15
 */
abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : BaseFrameFragment<VB, VM>()