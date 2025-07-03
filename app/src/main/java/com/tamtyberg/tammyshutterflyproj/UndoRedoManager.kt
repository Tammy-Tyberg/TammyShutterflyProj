package com.tamtyberg.tammyshutterflyproj

import androidx.compose.runtime.State
import com.tamtyberg.tammyshutterflyproj.ui.canvas.CanvasAction

interface UndoRedoManager {
    val canUndo: State<Boolean>
    val canRedo: State<Boolean>

    fun hasHistory(): Boolean
    fun perform(action: CanvasAction)
    fun undo()
    fun redo()
    fun clearRedo()
    fun clear()
}