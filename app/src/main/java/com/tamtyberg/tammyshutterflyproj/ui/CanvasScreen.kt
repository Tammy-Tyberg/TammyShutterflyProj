package com.tamtyberg.tammyshutterflyproj.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tamtyberg.tammyshutterflyproj.ui.canvas.CanvasActionButtons
import com.tamtyberg.tammyshutterflyproj.ui.canvas.CanvasBoxOrig
import com.tamtyberg.tammyshutterflyproj.ui.canvas.CanvasViewModel
import com.tamtyberg.tammyshutterflyproj.ui.carousel.Carousel
import com.tamtyberg.tammyshutterflyproj.ui.carousel.CarouselViewModel

@Composable
fun CanvasScreen(
    carouselViewModel: CarouselViewModel = hiltViewModel(),
    canvasViewModel: CanvasViewModel = hiltViewModel()
) {
    val carouselItems by carouselViewModel.items

    val canUndo by canvasViewModel.canUndo
    val canRedo by canvasViewModel.canRedo
    val canClear by canvasViewModel.canClear

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        CanvasActionButtons(
            canUndo = canUndo,
            canRedo = canRedo,
            canClear = canClear,
            onUndo = { canvasViewModel.onUndo() },
            onRedo = { canvasViewModel.onRedo() },
            onClear = { canvasViewModel.clearCanvas() }
        )
        Spacer(Modifier.padding(8.dp))
        CanvasBoxOrig(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            images = canvasViewModel.images,
            onDrop = { resId, x, y -> canvasViewModel.handleDrop(resId, x, y) },
            onTransform = { id, startZoom, startPan, endZoom, endPan ->
                canvasViewModel.handleTransform(
                    id,
                    startZoom,
                    startPan,
                    endZoom,
                    endPan
                )
            },
            onRaiseZIndex = { id -> canvasViewModel.raiseZIndex(id) },
            onLog = { s -> canvasViewModel.log(s) }
        )
        Spacer(Modifier.padding(16.dp))
        Carousel(items = carouselItems)
    }
}
