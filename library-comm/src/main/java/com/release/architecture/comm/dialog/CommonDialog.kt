package com.release.architecture.comm.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import com.release.architecture.comm.R
import com.release.architecture.comm.databinding.CommDialogBinding

/**
 * 提示Dialog
 * @author yancheng
 * @since 2021/7/22
 *
 */
class CommonDialog(
    context: Context,
    onConfimClickListener: () -> Unit,
    onCancelClickListener: () -> Unit
) : Dialog(context, R.style.comm_transparent_dialog) {

    private lateinit var mBinding: CommDialogBinding
    private var mOnConfimClickListener = onConfimClickListener
    private var mOnCancelClickListener = onCancelClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = CommDialogBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.vConfimBtn.setOnClickListener {
            mOnConfimClickListener()
            dismiss()
        }
        mBinding.vCancelBtn.setOnClickListener {
            mOnCancelClickListener()
            dismiss()
        }
        setCanceledOnTouchOutside(false)
        hideTheBottomNavigationBar()
    }

    fun setTitle(title: String) {
        mBinding.vTitle.text = title
    }

    fun setContent(content: String) {
        mBinding.vContentTv.text = content
    }

    fun setCancelText(cancelText: String) {
        mBinding.vCancelBtn.text = cancelText
    }

    fun setConfimText(confimText: String) {
        mBinding.vConfimBtn.text = confimText
    }

    /**
     * 隐藏底部导航栏
     * @return Unit
     */
    private fun hideTheBottomNavigationBar() {
        val params = window?.attributes
        params?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
        window?.attributes = params
    }
}