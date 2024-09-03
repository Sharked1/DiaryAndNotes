package com.example.diaryandnotes.ui.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object Transition {
    fun stdFadeIn(duration: Int = 300) = fadeIn(
        animationSpec =
            tween(
                durationMillis = duration,
                easing = LinearEasing
            )
    )
    fun stdFadeOut(duration: Int = 300) = fadeOut(
        animationSpec =
            tween(
                durationMillis = duration,
                easing = LinearEasing
            )
    )

    fun stdSlideOutHorizontally(targetOffsetX: Int) = (stdFadeOut() + slideOutHorizontally(
        animationSpec = tween(300, easing = LinearEasing),
        targetOffsetX = { it * targetOffsetX }
    ))

    fun stdSlideInHorizontally(initialOffsetX: Int) = (stdFadeIn() + slideInHorizontally(
        animationSpec = tween(300, easing = LinearEasing),
        initialOffsetX = { it * initialOffsetX }
    ))
}