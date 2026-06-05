package com.marin.thrikis

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.marin.thrikis.core.navigation.NavGraph
import com.marin.thrikis.data.network.BluetoothController
import com.marin.thrikis.ui.theme.ThrikisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BluetoothController.init(applicationContext)

        setContent {
            ThrikisTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}