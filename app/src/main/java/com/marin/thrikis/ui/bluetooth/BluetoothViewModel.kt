package com.marin.thrikis.ui.bluetooth

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.thrikis.data.network.BluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class BluetoothViewModel(context: Context) : ViewModel() {

    private val _pairedDevices = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val pairedDevices: StateFlow<List<Pair<String, String>>> = _pairedDevices.asStateFlow()

    private val _isHosting = MutableStateFlow(false)
    val isHosting: StateFlow<Boolean> = _isHosting.asStateFlow()

    val isConnected: StateFlow<Boolean> = BluetoothController.isConnected

    init {
        BluetoothController.init(context)
    }

    fun isBluetoothReady(): Boolean {
        return BluetoothController.isBluetoothEnabled()
    }

    fun loadPairedDevices() {
        if (!isBluetoothReady()) return
        val adapter = android.bluetooth.BluetoothAdapter.getDefaultAdapter()
        val devices = adapter?.bondedDevices
        if (devices != null) {
            _pairedDevices.value = devices.map { device ->
                device.name to device.address
            }
        }
    }

    fun startHosting() {
        if (!isBluetoothReady()) return
        _isHosting.value = true
        viewModelScope.launch {
            BluetoothController.startServer()
        }
    }

    fun connectToDevice(address: String) {
        if (!isBluetoothReady()) return
        _isHosting.value = false
        viewModelScope.launch {
            BluetoothController.connectToDevice(address)
        }
    }
}