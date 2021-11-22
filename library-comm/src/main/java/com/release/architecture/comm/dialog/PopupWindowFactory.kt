package com.release.architecture.comm.dialog

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.release.architecture.base.ktx.makeDropDownMeasureSpec
import com.release.architecture.comm.R
import com.release.architecture.comm.adapter.SelectListAdapter

/**
 * PopupWindow生产工厂
 * @author yancheng
 * @since 2021/7/21
 *
 */
class PopupWindowFactory {

    companion object {
        /**
         * 选择列表的PopupWindow
         * @param context Context
         * @param stringList MutableList<String>
         * @param onItemClickListenerBlock Function3<[@kotlin.ParameterName] SelectListAdapter, [@kotlin.ParameterName] View, [@kotlin.ParameterName] Int, Unit>
         * @param onDismissListenerBlock Function0<Unit>
         * @return PopupWindow
         */
        inline fun createSelectListPopupWindow(
            context: Context,
            width: Int,
            stringList: MutableList<String> = mutableListOf(),
            crossinline onItemClickListenerBlock: (adapter: SelectListAdapter, view: View, position: Int) -> Unit,
            crossinline onDismissListenerBlock: () -> Unit
        ): PopupWindow {
            val popView = View.inflate(context, R.layout.comm_popup_window_select_list, null)
            val vContainVc = popView.findViewById<CardView>(R.id.vContainVc)
            vContainVc.layoutParams.width = width
            val popupWindow = PopupWindow(
                popView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            popView.measure(
                popupWindow.makeDropDownMeasureSpec(popupWindow.width),
                popupWindow.makeDropDownMeasureSpec(popupWindow.height)
            )
            val chooseRv = popView.findViewById<RecyclerView>(R.id.mRv)
            val selectListAdapter = SelectListAdapter(stringList)
            chooseRv.layoutManager = LinearLayoutManager(context)
            chooseRv.adapter = selectListAdapter
            selectListAdapter.setOnItemClickListener { adapter, view, position ->
                onItemClickListenerBlock(adapter as SelectListAdapter, view, position)
                popupWindow.dismiss()
            }
            popupWindow.setBackgroundDrawable(BitmapDrawable())
            popupWindow.isOutsideTouchable = true
            popupWindow.isFocusable = true
            popupWindow.setOnDismissListener { onDismissListenerBlock() }
            return popupWindow
        }
    }
}