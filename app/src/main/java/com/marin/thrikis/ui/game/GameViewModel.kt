package com.marin.thrikis.ui.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.marin.thrikis.domain.model.SupremeBoard
import com.marin.thrikis.domain.model.Symbol
import com.marin.thrikis.domain.usecase.ProcessMoveUseCase

class GameViewModel : ViewModel() {

    private val processMoveUseCase = ProcessMoveUseCase()

    private val _boardState = MutableStateFlow(SupremeBoard())
    val boardState: StateFlow<SupremeBoard> = _boardState.asStateFlow()

    private val _currentTurn = MutableStateFlow(Symbol.PLAYER_ONE_BASE)
    val currentTurn: StateFlow<Symbol> = _currentTurn.asStateFlow()

    private val _gameMode = MutableStateFlow("local")
    val gameMode: StateFlow<String> = _gameMode.asStateFlow()

    fun initGame(mode: String) {
        _gameMode.value = mode
        _boardState.value = SupremeBoard()
        _currentTurn.value = Symbol.PLAYER_ONE_BASE
    }

    fun onCellClicked(macroRow: Int, macroCol: Int, microRow: Int, microCol: Int) {
        val currentBoard = _boardState.value
        val currentSymbol = _currentTurn.value

        val newBoard = processMoveUseCase.execute(
            board = currentBoard,
            macroRow = macroRow,
            macroCol = macroCol,
            microRow = microRow,
            microCol = microCol,
            playerSymbol = currentSymbol
        )

        if (newBoard != null) {
            _boardState.value = newBoard

            if (newBoard.globalWinner == Symbol.NONE) {
                _currentTurn.value = if (currentSymbol == Symbol.PLAYER_ONE_BASE) {
                    Symbol.PLAYER_TWO_BASE
                } else {
                    Symbol.PLAYER_ONE_BASE
                }
            }
        }
    }
}