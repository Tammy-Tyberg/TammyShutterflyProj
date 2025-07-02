package com.tamtyberg.tammyshutterflyproj.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { canvasViewModel.onUndo() },
                    enabled = canUndo
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Undo"
                    )
                }
                Text(text = "Undo", style = MaterialTheme.typography.labelSmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { canvasViewModel.onRedo() },
                    enabled = canRedo
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Redo"
                    )
                }
                Text(text = "Redo", style = MaterialTheme.typography.labelSmall)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = { canvasViewModel.clearCanvas() },
                    enabled = canClear
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Clear"
                    )
                }
                Text(text = "Clear", style = MaterialTheme.typography.labelSmall)
            }
        }

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
