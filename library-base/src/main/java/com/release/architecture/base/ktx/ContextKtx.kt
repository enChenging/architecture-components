package com.release.architecture.base.ktx

import android.app.Activity
import android.content.Context
import android.content.Intent

/**
 * Context相关扩展方法
 * @author yancheng
 * @since 2021/10/21
 */

inline fun <reified T:Activity> Context.startActivity(vararg params: Pair<String,String>){
    val intent  = Intent(this,T::class.java)
    params.forEach { intent.putExtra(it.first,it.second) }
    startActivity(intent)
}