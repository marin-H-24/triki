package com.marin.thrikis.ui.bluetooth

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun BluetoothScreen(
    viewModel: BluetoothViewModel,
    onConnectionSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val pairedDevices by viewModel.pairedDevices.collectAsState()
    val isConnected by viewModel.isConnected.collectAsState()

    val bluetoothPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        if (results.values.all { it }) {
            viewModel.loadPairedDevices()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(bluetoothPermissions)
    }

    LaunchedEffect(isConnected) {
        if (isConnected) {
            onConnectionSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF121212))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Duelo Bluetooth",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (!viewModel.isBluetoothReady()) {
                Text(
                    text = "Por favor, enciende el Bluetooth en los ajustes de tu dispositivo.",
                    color = Color(0xFFFF5252),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { viewModel.startHosting() },
                    enabled = viewModel.isBluetoothReady(),
                    modifier = Modifier.weight(1f).height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Crear Sala", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Dispositivos Vinculados",
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
            )

            if (pairedDevices.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay dispositivos vinculados en el sistema", color = Color.Gray, fontSize = 14.sp)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(pairedDevices) { (name, address) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF1E1E2F), RoundedCornerShape(8.dp))
                                .border(1.dp, Color(0xFF424242), RoundedCornerShape(8.dp))
                                .clickable(enabled = viewModel.isBluetoothReady()) {
                                    viewModel.connectToDevice(address)
                                }
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                Text(text = address, color = Color.Gray, fontSize = 12.sp)
                            }
                            Text(text = "Conectar", color = Color(0xFFFFD700), fontSize = 14.sp)
                        }
                    }
                }
            }
        }

        Button(
            onClick = onNavigateBack,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF252538)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Volver al Menú", fontSize = 16.sp, color = Color.White)
        }
    }
}