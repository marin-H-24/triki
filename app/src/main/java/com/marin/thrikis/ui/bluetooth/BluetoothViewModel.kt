package com.marin.thrikis.ui.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
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

    private val bluetoothController = BluetoothController(context)

    private val bluetoothAdapter: BluetoothAdapter? =
        context.getSystemService(BluetoothManager::class.java)?.adapter

    private val _pairedDevices = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val pairedDevices: StateFlow<List<Pair<String, String>>> = _pairedDevices.asStateFlow()

    val isConnected: StateFlow<Boolean> = bluetoothController.isConnected

    fun loadPairedDevices() {
        val devices = bluetoothAdapter?.bondedDevices
        if (devices != null) {
            _pairedDevices.value = devices.map { device ->
                device.name to device.address
            }
        }
    }

    fun startHosting() {
        viewModelScope.launch {
            bluetoothController.startServer()
        }
    }

    fun connectToDevice(address: String) {
        viewModelScope.launch {
            bluetoothController.connectToDevice(address)
        }
    }

    override fun onCleared() {
        super.onCleared()
        bluetoothController.disconnect()
    }
}