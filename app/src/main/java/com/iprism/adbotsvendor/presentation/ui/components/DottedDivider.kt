package com.iprism.adbotsvendor.presentation.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp

@Composable
fun DottedDivider() {
    val dividerColor = MaterialTheme.colorScheme.outlineVariant
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        drawLine(
            color = dividerColor,
            start = Offset.Zero,
            end = Offset(size.width, 0f),
            strokeWidth = 3f,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(8f, 8f),
                0f
            )
        )
    }
}