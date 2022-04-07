package com.release.architecture.common.proxy

import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.permissionx.guolindev.PermissionX

/**
 * 权限请求与界面进行解耦
 *
 */
class PermissionRequest {

    /**
     * 权限请求
     * @param activity FragmentActivity 当前申请权限请求的 activity
     * @param allGrantedCall allGrantedBlock: () -> Unit 全部授予回调
     * @param deniedCall deniedBlock: (deniedList: List<String>) -> Unit 有被拒绝回调
     */
    inline fun permissionRequest(
        activity: FragmentActivity,
        crossinline allGrantedCall: () -> Unit,
        crossinline deniedCall: (deniedList: List<String>) -> Unit
    ) {
        PermissionX.init(activity)
            .permissions(
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO
            )
            .request { allGranted, _, deniedList ->
                if (allGranted) allGrantedCall() else deniedCall(deniedList)
            }
    }
}