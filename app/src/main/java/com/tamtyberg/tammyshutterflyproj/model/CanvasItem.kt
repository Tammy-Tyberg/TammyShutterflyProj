package com.tamtyberg.tammyshutterflyproj.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

data class CanvasImage(
    val id: Int = generateId(),
    val resId: Int,
    var offsetX: Float,
    var offsetY: Float,
    val zIndex: MutableState<Float> = mutableStateOf(0f),
    val scale: MutableState<Float> = mutableStateOf(1f),
    val panOffset: MutableState<Offset> = mutableStateOf(Offset.Zero)
)

fun generateId(): Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()

