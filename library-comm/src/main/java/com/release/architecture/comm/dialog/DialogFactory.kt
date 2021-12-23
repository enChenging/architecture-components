package com.release.architecture.comm.dialog

import android.app.Dialog
import android.content.Context
import com.release.architecture.base.ktx.gone
import com.release.architecture.base.ktx.visible
import com.release.architecture.comm.R
import com.release.architecture.comm.databinding.CommDialogConfirmBinding
import com.release.architecture.comm.databinding.CommDialogConfirmCancelBinding
import com.release.architecture.comm.databinding.CommDialogConfirmCancelNoTitleBinding
import com.release.architecture.comm.databinding.CommDialogTipsBinding

/**
 * 统一弹窗
 * @author yancheng
 * @since 2021/7/22
 *
 */
class DialogFactory {

    companion object {

        /**
         * 提示的Dialog
         * @param context Context 上下文
         * @param titleText String 标题内容
         * @param content String 提示文本
         * @param sureText String 确定文本
         * @param canceledOnTouchOutside Boolean 点击外面是否取消显示 默认会取消
         * @param onConfirmListener () -> Unit 确认回调
         * @return Dialog
         */
        inline fun createTipsDialog(
            context: Context,
            titleText: String = "",
            content: String,
            sureText: String = "我知道了",
            canceledOnTouchOutside: Boolean = true,
            crossinline onConfirmListener: () -> Unit
        ): Dialog {
            val dialog = Dialog(context, R.style.comm_transparent_dialog)
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            CommDialogTipsBinding.inflate(dialog.layoutInflater).apply {
                vContentTv.text = content
                vSureBtn.text = sureText
                vTitleTv.text = titleText
                vSureBtn.setOnClickListener {
                    onConfirmListener()
                    dialog.dismiss()
                }
                if (titleText.isNotEmpty())
                    vTitleTv.visible()
                else
                    vTitleTv.gone()
                dialog.setContentView(root)
            }
            return dialog
        }

        /**
         * 创建一个带有确认和取消按键的Dialog
         * @param context Context 上下文
         * @param titleText String 标题文本
         * @param content String 内容文本
         * @param confirmText String 确认文本
         * @param cancelText String 取消文本
         * @param canceledOnTouchOutside Boolean
         * @param confirmCall () -> Unit 确认回调
         * @param cancelCall () -> Unit 取消回调
         * @return Dialog
         */
        inline fun createConfirmCancelDialog(
            context: Context,
            titleText: String,
            content: String,
            confirmText: String = "确定",
            cancelText: String = "取消",
            canceledOnTouchOutside: Boolean = true,
            crossinline confirmCall: () -> Unit,
            crossinline cancelCall: () -> Unit,
            crossinline closeCall: () -> Unit
        ): Dialog {
            val dialog = Dialog(context, R.style.comm_transparent_dialog)
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            CommDialogConfirmCancelBinding.inflate(dialog.layoutInflater).apply {
                vTitleTv.text = titleText
                vContentTv.text = content
                vCancelTv.text = cancelText
                vConfirmTv.text = confirmText
                vConfirmTv.setOnClickListener {
                    confirmCall()
                    dialog.dismiss()
                }
                vCancelTv.setOnClickListener {
                    cancelCall()
                    dialog.dismiss()
                }
                vCloseIv.setOnClickListener {
                    closeCall()
                    dialog.dismiss()
                }
                dialog.setContentView(root)
            }
            return dialog
        }

        /**
         * 创建一个带有确认和取消按键的Dialog
         * @param context Context 上下文
         * @param titleText String 标题文本
         * @param content String 内容文本
         * @param confirmText String 确认文本
         * @param canceledOnTouchOutside Boolean
         * @param confirmCall () -> Unit 确认回调
         * @return Dialog
         */
        inline fun createConfirmDialog(
            context: Context,
            titleText: String,
            content: String,
            confirmText: String = "确定",
            canceledOnTouchOutside: Boolean = true,
            crossinline confirmCall: () -> Unit,
            crossinline closeCall: () -> Unit
        ): Dialog {
            val dialog = Dialog(context, R.style.comm_transparent_dialog)
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            CommDialogConfirmBinding.inflate(dialog.layoutInflater).apply {
                vTitleTv.text = titleText
                vContentTv.text = content
                vConfirmTv.text = confirmText
                vConfirmTv.setOnClickListener {
                    confirmCall()
                    dialog.dismiss()
                }
                vCloseIv.setOnClickListener {
                    closeCall()
                    dialog.dismiss()
                }
                dialog.setContentView(root)
            }
            return dialog
        }

        /**
         * 创建一个没有标题的确认取消弹窗
         * @param context Context 上下文
         * @param content String 内容
         * @param confirmText String 确认文案
         * @param cancelText String 取消文案
         * @param canceledOnTouchOutside Boolean 是否点击其他区域消失
         * @param confirmCall () -> Unit 确认回调
         * @param cancelCall () -> Unit 取消回调
         * @param closeCall () -> Unit 关闭回调
         * @return Dialog
         */
        inline fun createConfirmCancelNoTitleDialog(
            context: Context,
            content: String,
            confirmText: String = "确定",
            cancelText: String = "取消",
            canceledOnTouchOutside: Boolean = true,
            crossinline confirmCall: () -> Unit,
            crossinline cancelCall: () -> Unit,
            crossinline closeCall: () -> Unit
        ): Dialog {
            val dialog = Dialog(context, R.style.comm_transparent_dialog)
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            CommDialogConfirmCancelNoTitleBinding.inflate(dialog.layoutInflater).apply {
                vContentTv.text = content
                vCancelTv.text = cancelText
                vConfirmTv.text = confirmText
                vConfirmTv.setOnClickListener {
                    confirmCall()
                    dialog.dismiss()
                }
                vCancelTv.setOnClickListener {
                    cancelCall()
                    dialog.dismiss()
                }
                vCloseIv.setOnClickListener {
                    closeCall()
                    dialog.dismiss()
                }
                dialog.setContentView(root)
            }
            return dialog
        }

    }
}