package com.marin.thrikis.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.marin.thrikis.domain.model.UserProfile

@Composable
fun MenuScreen(
    viewModel: MenuViewModel,
    onNavigateToGame: (String) -> Unit,
    onNavigateToProfile: (String) -> Unit,
    onNavigateToFriends: () -> Unit
) {
    val profile by viewModel.userProfile.collectAsState()

    profile?.let { user ->
        val backgroundColor = if (user.level >= 10) Color(0xFF1A1B2F) else Color(0xFF121212)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            HeaderProfileComponent(
                user = user,
                onProfileClick = { onNavigateToProfile(user.id) }
            )

            Text(
                text = "THRIKIS",
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MenuButton(text = "Multijugador Online", onClick = { onNavigateToGame("online") })
                MenuButton(text = "Duelo Bluetooth", onClick = { onNavigateToGame("bluetooth") })
                MenuButton(text = "Partida Local (1 vs 1)", onClick = { onNavigateToGame("local") })
            }

            Button(
                onClick = onNavigateToFriends,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Amigos y Social", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun HeaderProfileComponent(user: UserProfile, onProfileClick: () -> Unit) {
    Card(
        onClick = onProfileClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF252538)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = user.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Nivel ${user.level}",
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { (user.xp % 1000) / 1000f },
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFD700),
                    trackColor = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E1E2F)),
        shape = RoundedCornerShape(14.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White
        )
    }
}