package com.release.architecture.push

import cn.jpush.android.service.JCommonService

/**
 * 这个是自定义Service，要继承极光JCommonService，可以在更多手机平台上使得推送通道保持的更稳定
 *
 * @author yancheng
 * @since 2021/8/6
 */
class JCoreService : JCommonService()