package com.release.architecture

import com.release.architecture.base.BaseApp
import dagger.hilt.android.HiltAndroidApp
/**
 * 壳工程的 Application
 * 主要用作依赖入口注入点和优化 EventBus 反射效率
 *
 * @author yancheng
 * @since 2021/11/15
 */
@HiltAndroidApp
class App : BaseApp()