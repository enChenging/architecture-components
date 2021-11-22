package com.release.architecture.base.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * RecycleView 分割线
 *
 * @author yancheng
 * @create 2021/8/6
 * @Describe
 */
class SpaceItemDecoration(
    private val top: Int,
    private val bottom: Int,
    private val left: Int,
    private val right: Int
) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = left
        outRect.bottom = bottom
        outRect.top = top
        outRect.right = right
    }
}