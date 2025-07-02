package com.tamtyberg.tammyshutterflyproj.ui.canvas

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.tamtyberg.tammyshutterflyproj.model.CanvasImage
import com.tamtyberg.tammyshutterflyproj.util.detectTransformGesturesWithStartEnd

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CanvasBoxOrig(
    modifier: Modifier = Modifier,
    images: List<CanvasImage>,
    onDrop: (resId: Int, x: Float, y: Float) -> Unit,
    onTransform: (id: Int, startZoom: Float, startPan: Offset, endZoom: Float, endPan: Offset) -> Unit,
    onRaiseZIndex: (id: Int) -> Unit,
    onLog: (msg: String) -> Unit
) {
    var isDraggingOver by remember { mutableStateOf(false) }
    var dropBoxOffset by remember { mutableStateOf(IntOffset.Zero) }
    var dropBoxSize by remember { mutableStateOf(IntSize.Zero) }
    val imageSizePx = with(LocalDensity.current) { 100.dp.toPx() }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFFB0BEC5), RoundedCornerShape(16.dp))
            .shadow(6.dp, RoundedCornerShape(16.dp))
            .clipToBounds()
            .background(if (isDraggingOver) Color(0xFFE3F2FD) else Color(0xFFF8F8F8))
            .onGloballyPositioned { layout ->
                val pos = layout.positionInRoot()
                dropBoxOffset = IntOffset(pos.x.toInt(), pos.y.toInt())
                dropBoxSize = layout.size
            }
            .dragAndDropTarget(
                shouldStartDragAndDrop = { event ->
                    event.mimeTypes().contains("image/resource-id")
                },
                target = remember {
                    object : DragAndDropTarget {
                        override fun onEntered(event: DragAndDropEvent) {
                            isDraggingOver = true
                        }

                        override fun onExited(event: DragAndDropEvent) {
                            isDraggingOver = false
                        }

                        override fun onDrop(event: DragAndDropEvent): Boolean {
                            isDraggingOver = false
                            val dragEvent = event.toAndroidDragEvent()
                            val resId =
                                dragEvent.clipData.getItemAt(0).text?.toString()?.toIntOrNull()
                            if (resId != null) {
                                val x = dragEvent.x.toInt() - dropBoxOffset.x - (imageSizePx / 2)
                                val y = dragEvent.y.toInt() - dropBoxOffset.y - (imageSizePx / 2)
                                onDrop(resId, x.toFloat(), y.toFloat())
                            }
                            return true
                        }
                    }
                }
            )
    ) {
        if (images.isEmpty()) {
            Text(
                text = "Drop images here to start",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        images.forEach { image ->
            key(image.id) {
                val scale = image.scale
                val offset = image.panOffset
                var startScale by remember { mutableStateOf(1f) }
                var startOffset by remember { mutableStateOf(Offset.Zero) }
                Image(
                    painter = painterResource(id = image.resId),
                    contentDescription = null,
                    modifier = Modifier
                        .size(148.dp)
                        .zIndex(image.zIndex.value)
                        .offset { IntOffset(image.offsetX.toInt(), image.offsetY.toInt()) }
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                            translationX = offset.value.x * scale.value
                            translationY = offset.value.y * scale.value
                            transformOrigin = TransformOrigin(0f, 0f)
                        }
                        .pointerInput(image.id) {
                            detectTransformGesturesWithStartEnd(
                                onGestureStart = {
                                    Log.wtf("BOULA", "START")
                                    startScale = image.scale.value
                                    startOffset = image.panOffset.value
                                    onRaiseZIndex(image.id)
                                },
                                onGesture = { _, pan, gestureZoom, _ ->
                                    image.scale.value =
                                        (image.scale.value * gestureZoom).coerceIn(0.5f, 5f)
                                    image.panOffset.value += pan / image.scale.value
                                },
                                onGestureEnd = {
                                    Log.wtf("BOULA", "ENDDD")
                                    if (startScale != image.scale.value || startOffset != image.panOffset.value) {
                                        onTransform(
                                            image.id,
                                            startScale,
                                            startOffset,
                                            image.scale.value,
                                            image.panOffset.value
                                        )
                                    }
                                }
                            )
                        }
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }
    }
}
