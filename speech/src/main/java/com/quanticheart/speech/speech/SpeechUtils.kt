package com.quanticheart.speech.speech

import android.app.Activity
import android.content.Intent
import android.speech.SpeechRecognizer
import java.util.*

object SpeechUtils {
    fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
    }

    /**
     * Check if input matches
     *
     * @param listOfPossibleMatches list of string  with possible match
     * @param stringToMatch         string you want to match list
     * @return true if matched else false
     */
    fun findString(
        listOfPossibleMatches: ArrayList<String>?,
        stringToMatch: String?
    ): Boolean {
        if (null != listOfPossibleMatches) {
            for (transaltion in listOfPossibleMatches) {
                if (transaltion.contains(stringToMatch!!)) {
                    return true
                }
            }
        }
        return false
    }

    /**
     * Share on social media
     *
     * @param messageToShare message To Share
     * @param activity       context
     */
    fun share(messageToShare: String?, activity: Activity) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, messageToShare)
        activity.startActivity(Intent.createChooser(shareIntent, "Share using"))
    }
}