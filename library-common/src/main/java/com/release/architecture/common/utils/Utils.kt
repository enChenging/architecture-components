package com.release.architecture.common.utils

import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.view.ViewCompat
import com.release.architecture.base.app.ActivityStackManager
import com.release.architecture.common.net.helper.TokenManager


/**
 * 控制箭头的上下变化
 *
 * @param vArrowIv ImageView 展示图片的ImageView
 * @param arrTag Boolean 箭头的状态
 * @param isAnimate Boolean 是否需要动画
 */
fun setAnimatorArrowTag(vArrowIv: ImageView, arrTag: Boolean, isAnimate: Boolean) {
    if (arrTag) {
        if (isAnimate) {
            ViewCompat.animate(vArrowIv).setDuration(200)
                .setInterpolator(DecelerateInterpolator())
                .rotation(180f)
                .start()
        } else {
            vArrowIv.rotation = 180f
        }
    } else {
        if (isAnimate) {
            ViewCompat.animate(vArrowIv).setDuration(200)
                .setInterpolator(DecelerateInterpolator())
                .rotation(0f)
                .start()
        } else {
            vArrowIv.rotation = 0f
        }
    }
}

/**
 * 退出
 */
fun exit() {
    TokenManager.saveToken("")
    UserInfoCache.clearUserInfo()
    ActivityStackManager.finishAllActivity()
}