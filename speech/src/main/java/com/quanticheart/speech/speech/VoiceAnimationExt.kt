@file:Suppress("unused")

package com.quanticheart.speech.speech

import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation

private var animationFinish = true

fun View.cleanVoiceAnimation() {
    animationFinish = true
}

fun View.voiceAnimation(float: Float) {
    if (animationFinish) {
        animationFinish = false
        val scale = ScaleAnimation(
            1f,
            float.convertToScale(),
            1f,
            float.convertToScale(),
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scale.fillAfter = true
        scale.duration = 200
        scale.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                animationFinish = true
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        this.startAnimation(scale)
    }
}

private var maxRms = 1.5f
private var minRms = 1.05f
private fun Float.convertToScale(): Float {
    return if (this < 0)
        minRms
    else
        return if (this > maxRms)
            maxRms
        else
            this / 2
}