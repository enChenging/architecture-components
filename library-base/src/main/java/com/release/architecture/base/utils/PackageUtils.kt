package com.release.architecture.base.utils

import android.content.Context
import com.release.architecture.base.BaseApp

/**
 * App包工具类
 *
 */
object PackageUtils {

    /**
     * 判断指定app是否已经安装
     *
     * @param packageName String 指定app的包名
     * @return Boolean 是否安装
     */
    fun appIsInstalled(packageName: String): Boolean {
        // 获取包管理器
        val pm = BaseApp.context.packageManager
        // 获取当前设备安装的所有程序包名
        val installedPackages = pm.getInstalledPackages(0)
        return installedPackages.any { it.packageName == packageName }
    }

    /**
     * 启动一个指定包名[packageName]的app
     *
     * @param context Context 上下文
     * @param packageName String 需要启动的app包名
     */
    fun startApp(context: Context, packageName: String) {
        // 获取包管理器
        val pm = BaseApp.context.packageManager
        val intent = pm.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
    }

    /**
     * 获取设备上所有安装的应用包名列表
     * @return MutableList<String>
     */
    fun getDeviceAllInstalledPackages(): MutableList<String> {
        val packages = mutableListOf<String>()
        val installedPackages = BaseApp.context.packageManager.getInstalledPackages(0)
        installedPackages.forEach { packages.add(it.packageName) }
        return packages
    }

    /**
     * 获取设备上安装的应用包名列表 过滤掉了一部分系统应用
     * @return MutableList<String>
     */
    fun getFilterDeviceInstalledPackages(): MutableList<String> {
        val packages = mutableListOf<String>()
        val installedPackages = BaseApp.context.packageManager.getInstalledPackages(0)
        installedPackages.forEach {
            val packageName = it.packageName
            // 过滤
            if (packageName.startsWith("com.android.")
                || packageName.startsWith("android.")
                || packageName.startsWith("com.lenovo.")
            ) {
            } else {
                packages.add(packageName)
            }
        }
        return packages
    }
}