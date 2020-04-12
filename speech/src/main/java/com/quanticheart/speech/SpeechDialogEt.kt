package com.quanticheart.speech

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View

fun Context.speechDialog() {

    val view = LayoutInflater.from(this).inflate(R.layout.m_dialog_speech_default, null, false)
    val dialog = createDialog(view)

    speech({
        dialog.dismiss()
    }, {
        dialog.dismiss()
    }, {
        Log.e("RMS", it.toString())
    })
}

private fun Context.createDialog(layout: View): Dialog {
    val dialog = Dialog(this)
    dialog.setContentView(layout)
    dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    dialog.show()
    return dialog
}