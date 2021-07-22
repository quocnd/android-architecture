package com.quoc.coroutine.ui.dialog

import android.app.Activity
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import com.quoc.coroutine.R
import javax.inject.Inject

class DialogManagerImpl @Inject constructor(private val activity: Activity) : DialogManager {

    private var toast: Toast? = null
    private var dialog: AppCompatDialog? = null

    override fun toast(msg: String?) {
        toast?.cancel()
        toast = Toast.makeText(activity, msg.orEmpty(), Toast.LENGTH_LONG)
        toast?.show()
    }

    override fun showDialogTwoButton(
        title: String,
        msg: String,
        positiveListener: DialogInterface.OnClickListener?
    ) {
        showError(
            title = title,
            msg = msg,
            positiveListener = positiveListener
        )
    }

    private fun showError(
        title: String? = null,
        msg: String? = null,
        positiveText: String? = activity.getString(R.string.ok),
        negativeText: String? = activity.getString(R.string.cancel),
        positiveListener: DialogInterface.OnClickListener? = null,
        negativeListener: DialogInterface.OnClickListener? = null
    ) {
        dialog?.dismiss()
        val builder = AlertDialog.Builder(activity)
        if (!title.isNullOrEmpty()) {
            builder.setTitle(title.orEmpty())
        }
        if(!msg.isNullOrEmpty()) {
            builder.setMessage(msg.orEmpty())
        }
        if(!positiveText.isNullOrEmpty()) {
            builder.setPositiveButton(positiveText.orEmpty(), positiveListener)
        }
        if(!negativeText.isNullOrEmpty()) {
            builder.setNegativeButton(negativeText.orEmpty(), negativeListener)
        }
        dialog = builder.create()
        dialog?.show()
    }
}