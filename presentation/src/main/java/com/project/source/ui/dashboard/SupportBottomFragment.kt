package com.project.source.ui.dashboard

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.View

class SupportBottomFragment : BottomSheetDialogFragment() {

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val contentView = View.inflate(context, R.layout.fragment_bottom_support, null)

        contentView.callSupport.setOnClickListener {
            Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${getString(R.string.support_number)}")
            }.let {
                dismiss()
                startActivity(it)
            }
        }

        dialog.setContentView(contentView)
    }

    fun show(fm: FragmentManager) = super.show(fm, TAG)

    companion object {
        const val TAG = "SupportBottomFragment"
    }
}
