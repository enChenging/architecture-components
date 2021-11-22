package com.release.architecture.example

import android.app.Application
import android.content.Context
import com.google.auto.service.AutoService
import com.release.architecture.base.app.ApplicationLifecycle

/**
 * ExampleApp 模块的伪 Application
 *
 * @author yancheng
 * @since 2021/11/15
 */
@AutoService(ApplicationLifecycle::class)
class ExampleApp : ApplicationLifecycle {

    override fun onAttachBaseContext(context: Context) {}

    override fun onCreate(application: Application) {}

    override fun onTerminate(application: Application) {}

    override fun initByFrontDesk(): MutableList<() -> String> = mutableListOf()

    override fun initByBackstage() {}
}