package com.marin.thrikis.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.thrikis.domain.model.Symbol
import com.marin.thrikis.ui.game.components.SupremeBoardComponent

@Composable
fun GameScreen(
    mode: String,
    viewModel: GameViewModel,
    onNavigateBack: () -> Unit
) {
    val boardState by viewModel.boardState.collectAsState()
    val currentTurn by viewModel.currentTurn.collectAsState()

    LaunchedEffect(mode) {
        viewModel.initGame(mode)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderGameComponent(
                mode = mode,
                currentTurn = currentTurn,
                globalWinner = boardState.globalWinner
            )

            SupremeBoardComponent(
                supremeBoard = boardState,
                onCellClick = { macroRow, macroCol, microRow, microCol ->
                    viewModel.onCellClicked(macroRow, macroCol, microRow, microCol)
                },
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Button(
                onClick = onNavigateBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF252538)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Salir al Menú", fontSize = 16.sp, color = Color.White)
            }
        }

        if (boardState.globalWinner != Symbol.NONE) {
            WinnerOverlayComponent(
                winner = boardState.globalWinner,
                onDismiss = onNavigateBack
            )
        }
    }
}

@Composable
fun HeaderGameComponent(mode: String, currentTurn: Symbol, globalWinner: Symbol) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Modo: ${mode.replaceFirstChar { it.uppercase() }}",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (globalWinner == Symbol.NONE) {
            val turnText = if (currentTurn == Symbol.PLAYER_ONE_BASE) "Turno: Jugador 1 (Verde)" else "Turno: Jugador 2 (Rojo)"
            val turnColor = if (currentTurn == Symbol.PLAYER_ONE_BASE) Color(0xFF00E676) else Color(0xFFFF1744)
            Text(
                text = turnText,
                fontSize = 22.sp,
                color = turnColor,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "¡PARTIDA TERMINADA!",
                fontSize = 24.sp,
                color = Color(0xFFFFD700),
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
fun WinnerOverlayComponent(winner: Symbol, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.85f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)

        ) {
            val winnerName = if (winner == Symbol.PLAYER_ONE_BASE) "Jugador 1" else "Jugador 2"
            val winnerColor = if (winner == Symbol.PLAYER_ONE_BASE) Color(0xFF00E676) else Color(0xFFFF1744)

            Text(
                text = "¡VICTORIA SUPREMA!",
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                color = Color(0xFFFFD700)
            )
            Text(
                text = "Ganador: $winnerName",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = winnerColor
            )
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Continuar", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}