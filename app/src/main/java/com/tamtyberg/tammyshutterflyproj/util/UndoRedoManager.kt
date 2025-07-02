package com.tamtyberg.tammyshutterflyproj.util

import androidx.compose.runtime.mutableStateOf
import com.tamtyberg.tammyshutterflyproj.ui.canvas.CanvasAction


class UndoRedoManager() {
    private val undoStack = ArrayDeque<CanvasAction>()
    private val redoStack = ArrayDeque<CanvasAction>()

    val canUndo = mutableStateOf(false)
    val canRedo = mutableStateOf(false)

    fun hasHistory(): Boolean = undoStack.isNotEmpty() || redoStack.isNotEmpty()

    fun perform(action: CanvasAction) {
        action.execute()
        undoStack.addLast(action)
        redoStack.clear()
        updateFlags()
    }

    fun clearRedo() {
        redoStack.clear()
        updateFlags()
    }

    fun undo() {
        undoStack.removeLastOrNull()?.let {
            it.undo()
            redoStack.addLast(it)
            updateFlags()
        }
    }

    fun redo() {
        redoStack.removeLastOrNull()?.let {
            it.execute()
            undoStack.addLast(it)
            updateFlags()
        }
    }

    private fun updateFlags() {
        canUndo.value = undoStack.isNotEmpty()
        canRedo.value = redoStack.isNotEmpty()
    }

    fun clear() {
        undoStack.clear()
        redoStack.clear()
        updateFlags()
    }
}
