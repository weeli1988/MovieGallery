package com.example.moviegallery.views.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.moviegallery.R

open class BaseDialogFragment : AppCompatDialogFragment() {

    interface DismissListener {
        fun onDismiss(data: Bundle?)
    }

    private var dismissListener: DismissListener? = null
    private var data: Any? = null
    private lateinit var actionResult: ActionResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.MainTheme)
        isCancelable = false
        actionResult = ActionResult.OK
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        notifyDismissListener()
    }

    private fun notifyDismissListener() {
        if (dismissListener != null) {
            try {
                dismissListener!!.onDismiss(null)
            } catch (e: Exception) {

            }
        }
    }

    override fun dismiss() {
        dismissAllowingStateLoss()
    }

    fun setDismissListener(dismissListener: DismissListener) {
        this.dismissListener = dismissListener
    }

    fun setData(data: Any) {
        this.data = data
    }

    fun getData() : Any? {
        return data
    }

    fun setActionResult(actionResult: ActionResult) {
        this.actionResult = actionResult
    }

    fun getActionResult() : ActionResult {
        return actionResult
    }
}