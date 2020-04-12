@file:Suppress("SameParameterValue")

package com.quanticheart.speech

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.m_dialog_speech_default.view.*

@SuppressLint("InflateParams")
fun Context.speechDialog(title: String = "", callback: (String) -> Unit) {

    var firstResult = false

    val view = LayoutInflater.from(this).inflate(R.layout.m_dialog_speech_default, null, false)
    val dialog = createDialog(view)

    view.titleSpeech.text = title

    var dataResult: Bundle? = null

    getSpeechRecognizer(object : RecognitionListener {
        private val TAG = "RecognitionListener"

        override fun onReadyForSpeech(params: Bundle?) {
            Log.d(TAG, "onReadyForSpeech : $params")
        }

        override fun onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.d(TAG, "onRmsChanged")
            view.mSpeechLayoutScale.voiceAnimation(rmsdB)
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            Log.d(TAG, "onBufferReceived")
        }

        override fun onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech")
            view.labelResults.text = ".........."
        }

        override fun onError(error: Int) {
            val errorT = SpeechUtils.getErrorText(error)
            Log.e(TAG, "error $errorT")
            dialog.dismiss()
            view.mSpeechLayoutScale.cleanVoiceAnimation()
        }

        override fun onResults(results: Bundle?) {
            Log.e(TAG, "onResults $dataResult")
            results?.let {
                if (!firstResult) {
                    firstResult = true
                    dataResult = results
                    view.labelResults.text = it.getFirstTextInSpeechResults()
                    countdown(1) {
                        callback(dataResult.getFirstTextInSpeechResults())
                        dialog.dismiss()
                    }
                }
            }
        }

        override fun onPartialResults(partialResults: Bundle?) {
            Log.d(TAG, "onPartialResults")
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            Log.d(TAG, "onEvent $eventType")
        }
    })

}

private fun countdown(seconds: Long, finishCountDown: () -> Unit) {
    object : CountDownTimer(seconds * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {}

        override fun onFinish() {
            finishCountDown()
        }
    }.start()
}

private fun Bundle?.getFirstTextInSpeechResults(): String {
    return this?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.get(0) ?: ""
}

private fun Context.createDialog(layout: View): Dialog {
    val dialog = Dialog(this)
    dialog.setContentView(layout)
    dialog.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
    dialog.show()
    return dialog
}