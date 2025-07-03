package com.tamtyberg.tammyshutterflyproj.ui.canvas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CanvasActionButtons(
    canUndo: Boolean,
    canRedo: Boolean,
    canClear: Boolean,
    onUndo: () -> Unit,
    onRedo: () -> Unit,
    onClear: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(
            enabled = canUndo,
            onClick = onUndo,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            label = "Undo"
        )
        ActionButton(
            enabled = canRedo,
            onClick = onRedo,
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            label = "Redo"
        )
        ActionButton(
            enabled = canClear,
            onClick = onClear,
            icon = Icons.Default.Delete,
            label = "Clear"
        )
    }
}

@Composable
private fun ActionButton(
    enabled: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = onClick, enabled = enabled) {
            Icon(imageVector = icon, contentDescription = label)
        }
        Text(text = label, style = MaterialTheme.typography.labelSmall)
    }
}
