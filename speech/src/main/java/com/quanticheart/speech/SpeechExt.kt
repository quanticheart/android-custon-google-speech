package com.quanticheart.speech

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import com.quanticheart.speech.SpeechExt.speechActivityResponse
import java.util.*

object SpeechExt {
    const val speechActivityResponse = 10110
}

fun Activity.googleSpeech() {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "O que quer pesquisa?")
    try {
        startActivityForResult(intent, speechActivityResponse)
    } catch (a: ActivityNotFoundException) {
        Toast.makeText(
            this, "Error ao Pesquisar",
            Toast.LENGTH_SHORT
        ).show()
    }
}

fun Context.speech(
    callbackSuccess: (String) -> Unit,
    callbackError: (String) -> Unit,
    callbackRms: (Float) -> Unit
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
    // Use a language model based on free-form speech recognition.
    intent.putExtra(
        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
    )
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault())
    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)
    intent.putExtra(
        RecognizerIntent.EXTRA_CALLING_PACKAGE,
        packageName
    )
    // Add custom listeners.
    val sr = SpeechRecognizer.createSpeechRecognizer(this)
    sr.setRecognitionListener(object : RecognitionListener {
        private val TAG = "RecognitionListener"

        override fun onReadyForSpeech(params: Bundle?) {
            Log.d(TAG, "onReadyForSpeech")
        }

        override fun onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.d(TAG, "onRmsChanged")
            callbackRms(rmsdB)
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            Log.d(TAG, "onBufferReceived")
        }

        override fun onEndOfSpeech() {
            Log.d(TAG, "onEndofSpeech")
        }

        override fun onError(error: Int) {
            val errorT = SpeechUtils.getErrorText(error)
            Log.e(TAG, "error $errorT")
            callbackError(errorT)
        }

        override fun onResults(results: Bundle?) {
            val result = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.e(TAG, "onResults $results")
            result?.let {
                callbackSuccess(if (it.size > 0) it[0] else "")
                sr.destroy()
            } ?: run {
                callbackSuccess("")
            }
        }

        override fun onPartialResults(partialResults: Bundle?) {
            Log.d(TAG, "onPartialResults")
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            Log.d(TAG, "onEvent $eventType")
        }
    })
    sr.startListening(intent)
}
