package com.marin.thrikis.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    onNavigateToGame: (String) -> Unit,
    onNavigateToProfile: (String) -> Unit,
    onNavigateToFriends: () -> Unit
) {
    val profile by viewModel.userProfile.collectAsState()
    val xpRequired = profile.level * 100
    val xpProgress = profile.currentXp.toFloat() / xpRequired.toFloat()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable { onNavigateToProfile(profile.name) }
                .padding(8.dp)
        ) {
            Text(text = profile.name, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Nivel ${profile.level}", color = Color.LightGray, fontSize = 14.sp)
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

        Text(
            text = "THRIKIS",
            color = Color.White,
            fontSize = 40.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = 2.sp
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            MenuButton("Multijugador Online") { onNavigateToGame("online") }
            MenuButton("Duelo Bluetooth") { onNavigateToGame("bluetooth") }
            MenuButton("Partida Local (1 vs 1)") { onNavigateToGame("local") }
        }

        Button(
            onClick = onNavigateToFriends,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Amigos y Social", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF252538)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(text = text, fontSize = 16.sp, color = Color.White)
    }
}