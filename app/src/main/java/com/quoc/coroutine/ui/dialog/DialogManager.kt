package com.quoc.coroutine.ui.dialog

import android.content.DialogInterface

interface DialogManager {
    fun toast(msg: String?)

    fun showDialogTwoButton(title: String, msg: String, positiveListener: DialogInterface.OnClickListener?)
}