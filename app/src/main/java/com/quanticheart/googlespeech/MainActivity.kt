@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.quanticheart.googlespeech

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.quanticheart.speech.speech.SpeechExt
import com.quanticheart.speech.speech.googleSpeech
import com.quanticheart.speech.speech.speechDialog
import com.quanticheart.speech.viewmodel.SpeechRecognizerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1.setOnClickListener {
            speechDialog("Oque você quer pesquisar?") {
                Log.e("RESULTS", it)
            }
        }

        btn2.setOnClickListener {
            googleSpeech()
        }

        btn3.setOnClickListener {
            startActivity(Intent(this, SpeechRecognizerActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SpeechExt.speechActivityResponse -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        val result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        Log.e("RESULTS", result.toString())
                    }
                }
            }
        }
    }
}
