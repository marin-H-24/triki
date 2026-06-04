package com.marin.thrikis.domain.usecase

import com.marin.thrikis.domain.model.SubBoard
import com.marin.thrikis.domain.model.Symbol
import com.marin.thrikis.domain.model.SupremeBoard

class ProcessMoveUseCase {

    fun execute(
        board: SupremeBoard,
        macroRow: Int,
        macroCol: Int,
        microRow: Int,
        microCol: Int,
        playerSymbol: Symbol
    ): SupremeBoard? {
        if (!isValidMove(board, macroRow, macroCol, microRow, microCol)) {
            return null
        }

        val targetSub = board.boards[macroRow][macroCol]
        val newCells = targetSub.cells.mapIndexed { r, row ->
            row.mapIndexed { c, symbol ->
                if (r == microRow && c == microCol) playerSymbol else symbol
            }
        }

        var newWonBy = targetSub.wonBy
        if (checkThreeInARow(newCells, playerSymbol)) {
            newWonBy = playerSymbol
        }

        val newSubBoard = targetSub.copy(cells = newCells, wonBy = newWonBy)

        val newBoards = board.boards.mapIndexed { r, row ->
            row.mapIndexed { c, sub ->
                if (r == macroRow && c == macroCol) newSubBoard else sub
            }
        }

        var newGlobalWinner = board.globalWinner
        if (checkMacroVictory(newBoards, playerSymbol)) {
            newGlobalWinner = playerSymbol
        }

        val finalTargetSub = newBoards[microRow][microCol]
        val (nextRow, nextCol) = if (finalTargetSub.wonBy != Symbol.NONE) {
            null to null
        } else {
            microRow to microCol
        }

        return board.copy(
            boards = newBoards,
            nextRequiredRow = nextRow,
            nextRequiredCol = nextCol,
            globalWinner = newGlobalWinner
        )
    }

    private fun isValidMove(
        board: SupremeBoard,
        macroRow: Int,
        macroCol: Int,
        microRow: Int,
        microCol: Int
    ): Boolean {
        if (board.globalWinner != Symbol.NONE) return false

        val requiredRow = board.nextRequiredRow
        val requiredCol = board.nextRequiredCol
        if (requiredRow != null && requiredCol != null) {
            if (macroRow != requiredRow || macroCol != requiredCol) {
                return false
            }
        }

        val targetSubBoard = board.boards[macroRow][macroCol]
        if (targetSubBoard.wonBy != Symbol.NONE) return false

        return targetSubBoard.cells[microRow][microCol] == Symbol.NONE
    }

    private fun checkThreeInARow(cells: List<List<Symbol>>, symbol: Symbol): Boolean {
        for (i in 0..2) {
            if (cells[i][0] == symbol && cells[i][1] == symbol && cells[i][2] == symbol) return true
            if (cells[0][i] == symbol && cells[1][i] == symbol && cells[2][i] == symbol) return true
        }
        if (cells[0][0] == symbol && cells[1][1] == symbol && cells[2][2] == symbol) return true
        if (cells[0][2] == symbol && cells[1][1] == symbol && cells[2][0] == symbol) return true
        return false
    }

    private fun checkMacroVictory(boards: List<List<SubBoard>>, symbol: Symbol): Boolean {
        for (i in 0..2) {
            if (boards[i][0].wonBy == symbol && boards[i][1].wonBy == symbol && boards[i][2].wonBy == symbol) return true
            if (boards[0][i].wonBy == symbol && boards[1][i].wonBy == symbol && boards[2][i].wonBy == symbol) return true
        }
        if (boards[0][0].wonBy == symbol && boards[1][1].wonBy == symbol && boards[2][2].wonBy == symbol) return true
        if (boards[0][2].wonBy == symbol && boards[1][1].wonBy == symbol && boards[2][0].wonBy == symbol) return true
        return false
    }
}