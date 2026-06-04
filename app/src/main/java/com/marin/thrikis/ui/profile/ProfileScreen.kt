package com.marin.thrikis.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.thrikis.ui.profile.components.SymbolGridComponent

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigateBack: () -> Unit
) {
    val profileState by viewModel.profileState.collectAsState()
    val xpRequired = viewModel.getXpForNextLevel(profileState.level)
    val xpProgress = profileState.currentXp.toFloat() / xpRequired.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Perfil y Colección",
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E1E2F), RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column {
                Text(text = profileState.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Nivel ${profileState.level}", color = Color(0xFFFFD700), fontWeight = FontWeight.Bold)
                    Text(text = "${profileState.currentXp} / $xpRequired XP", color = Color.LightGray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { xpProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color(0xFFFFD700),
                    trackColor = Color(0xFF424242)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Inventario de Símbolos",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.Start)
        )

        Box(modifier = Modifier.weight(1f)) {
            SymbolGridComponent(
                unlockedIds = profileState.unlockedSymbolsIds,
                equippedId = profileState.equippedSymbolId,
                onSymbolSelected = { id -> viewModel.equipSymbol(id) }
            )
        }

        Button(
            onClick = onNavigateBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF252538)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Volver al Menú", fontSize = 16.sp, color = Color.White)
        }
    }
}