package com.marin.thrikis.ui.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.marin.thrikis.domain.model.Symbol

@Composable
fun SymbolRenderer(symbol: Symbol, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize().padding(12.dp)) {
        when (symbol) {
            Symbol.PLAYER_ONE_BASE -> {
                drawCircle(
                    color = Color(0xFF00E676),
                    radius = size.minDimension / 2f,
                    style = Stroke(width = 12f)
                )
            }
            Symbol.PLAYER_TWO_BASE -> {
                val strokeWidth = 12f
                drawLine(
                    color = Color(0xFFFF1744),
                    start = Offset(0f, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
                drawLine(
                    color = Color(0xFFFF1744),
                    start = Offset(size.width, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = strokeWidth
                )
            }
            Symbol.NONE -> {}
        }
    }
}