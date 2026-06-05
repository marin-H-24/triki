package com.marin.thrikis.data.network

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class BluetoothController(context: Context) {

    private val bluetoothAdapter: BluetoothAdapter? =
        context.getSystemService(BluetoothManager::class.java)?.adapter

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _incomingMessage = MutableStateFlow<String?>(null)
    val incomingMessage: StateFlow<String?> = _incomingMessage.asStateFlow()

    private var currentSocket: BluetoothSocket? = null
    private var serverSocket: BluetoothServerSocket? = null

    private val serviceUUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    suspend fun startServer() {
        withContext(Dispatchers.IO) {
            try {
                serverSocket = bluetoothAdapter?.listenUsingRfcommWithServiceRecord("Thrikis", serviceUUID)
                val socket = serverSocket?.accept()
                if (socket != null) {
                    currentSocket = socket
                    _isConnected.value = true
                    listenForData()
                }
            } catch (e: IOException) {
                _isConnected.value = false
            }
        }
    }

    suspend fun connectToDevice(deviceAddress: String) {
        withContext(Dispatchers.IO) {
            try {
                val device = bluetoothAdapter?.getRemoteDevice(deviceAddress)
                currentSocket = device?.createRfcommSocketToServiceRecord(serviceUUID)
                currentSocket?.connect()
                _isConnected.value = true
                listenForData()
            } catch (e: IOException) {
                _isConnected.value = false
            }
        }
    }

    fun sendData(data: String) {
        try {
            currentSocket?.outputStream?.write(data.toByteArray())
        } catch (e: IOException) {
            _isConnected.value = false
        }
    }

    private suspend fun listenForData() {
        withContext(Dispatchers.IO) {
            val buffer = ByteArray(1024)
            while (_isConnected.value) {
                try {
                    val bytes = currentSocket?.inputStream?.read(buffer) ?: 0
                    if (bytes > 0) {
                        val message = String(buffer, 0, bytes)
                        _incomingMessage.value = message
                    }
                } catch (e: IOException) {
                    _isConnected.value = false
                    break
                }
            }
        }
    }

    fun disconnect() {
        try {
            serverSocket?.close()
            currentSocket?.close()
            _isConnected.value = false
        } catch (e: IOException) {
        }
    }
}