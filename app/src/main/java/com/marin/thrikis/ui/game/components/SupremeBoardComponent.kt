package com.marin.thrikis.ui.game.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.marin.thrikis.domain.model.SupremeBoard

@Composable
fun SupremeBoardComponent(
    supremeBoard: SupremeBoard,
    onCellClick: (Int, Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth().aspectRatio(1f),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (macroRow in 0..2) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (macroCol in 0..2) {
                    val isRequired = supremeBoard.nextRequiredRow == macroRow && supremeBoard.nextRequiredCol == macroCol
                    val freeMove = supremeBoard.nextRequiredRow == null || supremeBoard.nextRequiredCol == null
                    val isHighlighted = isRequired || (freeMove && supremeBoard.boards[macroRow][macroCol].wonBy == com.marin.thrikis.domain.model.Symbol.NONE)

                    SubBoardComponent(
                        subBoard = supremeBoard.boards[macroRow][macroCol],
                        isHighlighted = isHighlighted,
                        onCellClick = { microRow, microCol ->
                            onCellClick(macroRow, macroCol, microRow, microCol)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}