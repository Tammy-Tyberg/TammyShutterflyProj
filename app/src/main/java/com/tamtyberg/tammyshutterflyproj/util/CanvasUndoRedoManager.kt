package com.tamtyberg.tammyshutterflyproj.util

import androidx.compose.runtime.mutableStateOf
import com.tamtyberg.tammyshutterflyproj.UndoRedoManager
import com.tamtyberg.tammyshutterflyproj.ui.canvas.CanvasAction
import androidx.compose.runtime.State
import javax.inject.Inject


class CanvasUndoRedoManager @Inject constructor(): UndoRedoManager {
    private val undoStack = ArrayDeque<CanvasAction>()
    private val redoStack = ArrayDeque<CanvasAction>()

    private val _canUndo = mutableStateOf(false)
    override val canUndo: State<Boolean> get() = _canUndo

    private val _canRedo = mutableStateOf(false)
    override val canRedo: State<Boolean> get() = _canRedo


    override fun hasHistory(): Boolean = undoStack.isNotEmpty() || redoStack.isNotEmpty()

    override fun perform(action: CanvasAction) {
        action.execute()
        undoStack.addLast(action)
        redoStack.clear()
        updateFlags()
    }

   override fun clearRedo() {
        redoStack.clear()
        updateFlags()
    }

   override fun undo() {
        undoStack.removeLastOrNull()?.let {
            it.undo()
            redoStack.addLast(it)
            updateFlags()
        }
    }

    override fun redo() {
        redoStack.removeLastOrNull()?.let {
            it.execute()
            undoStack.addLast(it)
            updateFlags()
        }
    }

    private fun updateFlags() {
        _canUndo.value = undoStack.isNotEmpty()
        _canRedo.value = redoStack.isNotEmpty()
    }

    override fun clear() {
        undoStack.clear()
        redoStack.clear()
        updateFlags()
    }
}
