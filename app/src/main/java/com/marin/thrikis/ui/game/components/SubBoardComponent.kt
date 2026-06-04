package com.marin.thrikis.ui.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.marin.thrikis.domain.model.SubBoard
import com.marin.thrikis.domain.model.Symbol

@Composable
fun SubBoardComponent(
    subBoard: SubBoard,
    isHighlighted: Boolean,
    onCellClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val alphaValue = if (isHighlighted || subBoard.wonBy != Symbol.NONE) 1.0f else 0.4f
    val borderColor = if (isHighlighted) Color(0xFFFFD700) else Color(0xFF424242)

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .alpha(alphaValue)
            .border(2.dp, borderColor, RoundedCornerShape(8.dp))
            .background(Color(0xFF1E1E2F))
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (subBoard.wonBy != Symbol.NONE) {
            SymbolRenderer(
                symbol = subBoard.wonBy,
                modifier = Modifier.fillMaxSize().alpha(0.8f)
            )
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                for (row in 0..2) {
                    Row(modifier = Modifier.weight(1f)) {
                        for (col in 0..2) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxSize()
                                    .border(0.5.dp, Color(0xFF555555))
                                    .clickable(enabled = subBoard.cells[row][col] == Symbol.NONE) {
                                        onCellClick(row, col)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                SymbolRenderer(symbol = subBoard.cells[row][col])
                            }
                        }
                    }
                }
            }
        }
    }
}