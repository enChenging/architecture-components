package com.release.architecture.comm.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.release.architecture.comm.R

/**
 * 选择列表Adapter
 * @author yancheng
 * @since 2021/7/21
 *
 */
class SelectListAdapter(list: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.comm_item_select, list) {

    /**
     * 选中item的下标
     */
    private var selectedIndex = 0

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.mTv, item)
        if (selectedIndex == holder.layoutPosition) {
            holder.setBackgroundColor(R.id.mRootLayout, 0x1A8273EF)
                .setTextColor(R.id.mTv, 0xFF8273EF.toInt())
            holder.setVisible(R.id.vRightIv, true)
        } else {
            holder.setBackgroundColor(R.id.mRootLayout, Color.WHITE)
                .setTextColor(R.id.mTv, 0xCC000000.toInt())
            holder.setGone(R.id.vRightIv, true)
        }
    }

    /**
     * 设置选中的item
     * @param position Int item 的 position
     */
    fun setSelectedItem(position: Int) {
        selectedIndex = position
        notifyDataSetChanged()
    }

    /**
     * 重置选中的item 不刷新界面
     */
    fun resetSelectedItem() {
        selectedIndex = 0
    }
}