package com.tamtyberg.tammyshutterflyproj.ui.canvas

import android.util.Log
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.tamtyberg.tammyshutterflyproj.util.CanvasUndoRedoManager
import com.tamtyberg.tammyshutterflyproj.model.CanvasImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf

@HiltViewModel
class CanvasViewModel @Inject constructor(
    private val canvasUndoRedoManager: CanvasUndoRedoManager
) : ViewModel() {

    val canUndo = canvasUndoRedoManager.canUndo
    val canRedo = canvasUndoRedoManager.canRedo

    private val _images = mutableStateListOf<CanvasImage>()
    val images: List<CanvasImage> get() = _images

    private var zIndexCounter = mutableFloatStateOf(1f)

    val canClear: State<Boolean>
        get() = derivedStateOf {
            _images.isNotEmpty() || canvasUndoRedoManager.hasHistory()
        }

    fun handleDrop(resId: Int, dropX: Float, dropY: Float) {
        canvasUndoRedoManager.clearRedo()
        val ci = CanvasImage(
            resId = resId,
            offsetX = dropX,
            offsetY = dropY,
            zIndex = mutableStateOf(zIndexCounter.floatValue++)
        )
        canvasUndoRedoManager.perform(DropImageCommand(this, ci))
    }

    fun handleTransform(
        imageId: Int,
        startZoom: Float,
        startPan: Offset,
        endZoom: Float,
        endPan: Offset
    ) {
        _images.find { it.id == imageId }?.let { image ->
            Log.d(
                "CanvasViewModel",
                "handle transform startScale: $startZoom toScale $endZoom startOffset $startPan   toOffset $endPan"
            )
            canvasUndoRedoManager.perform(
                TransformImageCommand(
                    viewModel = this,
                    imageId = image.id,
                    fromScale = startZoom,
                    fromOffset = startPan,
                    toScale = endZoom,
                    toOffset = endPan
                )
            )
        }
    }

    fun raiseZIndex(imageId: Int) {
        _images.find { it.id == imageId }?.let { image ->
            image.zIndex.value = zIndexCounter.floatValue++
        }
    }

    fun clearCanvas() {
        if(_images.isNotEmpty() || canvasUndoRedoManager.hasHistory()) {
            _images.clear()
            canvasUndoRedoManager.clear()
        }
    }

    fun addImage(image: CanvasImage) {
        _images.add(image)
    }

    fun removeImage(id: Int) {
        _images.removeIf { it.id == id }
    }

    fun updateImageTransform(
        imageId: Int,
        scale: Float? = null,
        offset: Offset? = null
    ) {
        _images.find { it.id == imageId }?.let { image ->
            scale?.let { image.scale.value = it }
            offset?.let { image.panOffset.value = it }
        }
    }

    fun onUndo() = canvasUndoRedoManager.undo()
    fun onRedo() = canvasUndoRedoManager.redo()
}
