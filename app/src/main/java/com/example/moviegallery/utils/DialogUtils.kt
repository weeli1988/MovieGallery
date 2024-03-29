package com.example.moviegallery.utils

import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog

object DialogUtils {

    private var dialog : AlertDialog? = null

    fun showProgressDialog(context: Context, title: String) : AlertDialog {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)

        val progressBar = ProgressBar(context)
        progressBar.isIndeterminate = true

        builder.setView(progressBar)

        val dialog = builder.create()
        dialog.setCancelable(false)
        dialog.show()

        return dialog
    }

    fun dismissDialog() {
        if (dialog!!.isShowing)
            dialog!!.dismiss()
    }

    fun setDialog(dialog: AlertDialog) {
        this.dialog = dialog
    }
}