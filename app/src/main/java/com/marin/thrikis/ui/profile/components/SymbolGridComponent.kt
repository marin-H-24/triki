package com.marin.thrikis.ui.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.marin.thrikis.domain.model.Symbol
import com.marin.thrikis.ui.game.components.SymbolRenderer

@Composable
fun SymbolGridComponent(
    unlockedIds: List<Int>,
    equippedId: Int,
    onSymbolSelected: (Int) -> Unit
) {
    val allSymbols = Symbol.values().filter { it != Symbol.NONE }

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.fillMaxSize().padding(vertical = 8.dp)
    ) {
        items(allSymbols) { symbol ->
            val isUnlocked = unlockedIds.contains(symbol.id)
            val isEquipped = equippedId == symbol.id

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .background(
                        color = if (isEquipped) Color(0xFF3F51B5) else Color(0xFF1E1E2F),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        width = if (isEquipped) 2.dp else 1.dp,
                        color = if (isEquipped) Color(0xFFFFD700) else Color(0xFF424242),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(enabled = isUnlocked) {
                        onSymbolSelected(symbol.id)
                    }
                    .alpha(if (isUnlocked) 1f else 0.3f),
                contentAlignment = Alignment.Center
            ) {
                if (isUnlocked) {
                    SymbolRenderer(
                        symbol = symbol,
                        modifier = Modifier.padding(12.dp)
                    )
                } else {
                    Text("?", color = Color.White)
                }
            }
        }
    }
}