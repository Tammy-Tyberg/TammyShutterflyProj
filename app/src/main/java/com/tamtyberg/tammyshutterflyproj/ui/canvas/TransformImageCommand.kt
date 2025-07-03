package com.tamtyberg.tammyshutterflyproj.ui.canvas

import android.util.Log
import androidx.compose.ui.geometry.Offset

class TransformImageCommand(
    private val viewModel: CanvasViewModel,
    private val imageId: Int,
    private val fromScale: Float,
    private val fromOffset: Offset,
    private val toScale: Float,
    private val toOffset: Offset
) : CanvasAction {
    override fun execute() {
//        Log.d(
//            "TransformImageCommand",
//            "exectue image $imageId to scale=$fromScale offset=$fromOffset"
//        )
        viewModel.updateImageTransform(imageId, toScale, toOffset)
    }

    override fun undo() {
//        Log.d("TransformImageCommand", "Undo image $imageId to scale=$fromScale offset=$fromOffset")
        viewModel.updateImageTransform(imageId, fromScale, fromOffset)
    }
}
