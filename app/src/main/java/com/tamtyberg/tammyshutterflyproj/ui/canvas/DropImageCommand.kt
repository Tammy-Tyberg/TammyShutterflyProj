package com.tamtyberg.tammyshutterflyproj.ui.canvas

import com.tamtyberg.tammyshutterflyproj.model.CanvasImage

class DropImageCommand(
    private val viewModel: CanvasViewModel,
    private val image: CanvasImage
) : CanvasAction {

    override fun execute() {
        viewModel.addImage(image)
    }

    override fun undo() {
        viewModel.removeImage(image.id)
    }
}
