package com.marin.thrikis.ui.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.marin.thrikis.data.network.BluetoothController
import com.marin.thrikis.domain.model.SupremeBoard
import com.marin.thrikis.domain.model.Symbol
import com.marin.thrikis.domain.usecase.ProcessMoveUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private val processMoveUseCase = ProcessMoveUseCase()
    private val bluetoothController = BluetoothController(application.applicationContext)

    private val _boardState = MutableStateFlow(SupremeBoard())
    val boardState: StateFlow<SupremeBoard> = _boardState.asStateFlow()

    private val _currentTurn = MutableStateFlow(Symbol.PLAYER_ONE_BASE)
    val currentTurn: StateFlow<Symbol> = _currentTurn.asStateFlow()

    private val _gameMode = MutableStateFlow("local")
    val gameMode: StateFlow<String> = _gameMode.asStateFlow()

    private var myRoleSymbol: Symbol = Symbol.PLAYER_ONE_BASE

    fun initGame(mode: String) {
        _gameMode.value = mode
        _boardState.value = SupremeBoard()
        _currentTurn.value = Symbol.PLAYER_ONE_BASE

        if (mode == "bluetooth") {
            listenForBluetoothMoves()
        }
    }

    fun onCellClicked(macroRow: Int, macroCol: Int, microRow: Int, microCol: Int) {
        val currentSymbol = _currentTurn.value

        if (_gameMode.value == "bluetooth" && currentSymbol != myRoleSymbol) {
            return
        }

        val success = applyMoveToBoard(macroRow, macroCol, microRow, microCol, currentSymbol)

        if (success && _gameMode.value == "bluetooth") {
            val message = "$macroRow,$macroCol,$microRow,$microCol"
            bluetoothController.sendData(message)
        }
    }

    private fun applyMoveToBoard(macroRow: Int, macroCol: Int, microRow: Int, microCol: Int, symbol: Symbol): Boolean {
        val currentBoard = _boardState.value
        val newBoard = processMoveUseCase.execute(
            board = currentBoard,
            macroRow = macroRow,
            macroCol = macroCol,
            microRow = microRow,
            microCol = microCol,
            playerSymbol = symbol
        )

        return if (newBoard != null) {
            _boardState.value = newBoard
            if (newBoard.globalWinner == Symbol.NONE) {
                _currentTurn.value = if (symbol == Symbol.PLAYER_ONE_BASE) {
                    Symbol.PLAYER_TWO_BASE
                } else {
                    Symbol.PLAYER_ONE_BASE
                }
            }
            true
        } else {
            false
        }
    }

    private fun listenForBluetoothMoves() {
        viewModelScope.launch {
            bluetoothController.incomingMessage.collect { message ->
                if (!message.isNullOrEmpty()) {
                    val parts = message.split(",")
                    if (parts.size == 4) {
                        val macroRow = parts[0].toIntOrNull()
                        val macroCol = parts[1].toIntOrNull()
                        val microRow = parts[2].toIntOrNull()
                        val microCol = parts[3].toIntOrNull()

                        if (macroRow != null && macroCol != null && microRow != null && microCol != null) {
                            val opponentSymbol = if (myRoleSymbol == Symbol.PLAYER_ONE_BASE) {
                                Symbol.PLAYER_TWO_BASE
                            } else {
                                Symbol.PLAYER_ONE_BASE
                            }
                            applyMoveToBoard(macroRow, macroCol, microRow, microCol, opponentSymbol)
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (_gameMode.value == "bluetooth") {
            bluetoothController.disconnect()
        }
    }
}