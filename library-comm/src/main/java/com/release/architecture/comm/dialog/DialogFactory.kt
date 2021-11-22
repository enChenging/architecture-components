package com.release.architecture.comm.dialog

import android.app.Dialog
import android.content.Context
import com.release.architecture.base.ktx.gone
import com.release.architecture.base.ktx.visible
import com.release.architecture.comm.R
import com.release.architecture.comm.databinding.CommDialogTipsBinding

/**
 *
 * @author yancheng
 * @since 2021/7/22
 *
 */
class DialogFactory {

    companion object {


        /**
         * 提示的Dialog
         * @param context Context 上下文
         * @param content String 提示文本
         * @param sureText String 确定文本
         * @param onConfirmListener () -> Unit 确认回调
         * @param canceledOnTouchOutside Boolean 点击外面是否取消显示 默认会取消
         * @param isShowTitle Boolean 是否显示标题 默认不显示
         * @param titleText String 标题内容
         * @return Dialog
         */
        inline fun createTipsDialog(
            context: Context,
            content: String,
            sureText: String,
            crossinline onConfirmListener: () -> Unit,
            canceledOnTouchOutside: Boolean = true,
            isShowTitle: Boolean = false,
            titleText: String = ""
        ): Dialog {
            val dialog = Dialog(context, R.style.comm_transparent_dialog)
            val viewBinding = CommDialogTipsBinding.inflate(dialog.layoutInflater)
            viewBinding.vContentTv.text = content
            viewBinding.vSureBtn.text = sureText
            viewBinding.vSureBtn.setOnClickListener {
                onConfirmListener()
                dialog.dismiss()
            }
            if (isShowTitle) {
                viewBinding.vTitleTv.text = titleText
                viewBinding.vTitleTv.visible()
            } else {
                viewBinding.vTitleTv.gone()
            }
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
            dialog.setContentView(viewBinding.root)
            return dialog
        }
    }
}