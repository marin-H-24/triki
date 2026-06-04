package com.marin.thrikis.domain.model

data class SupremeBoard(
    val boards: List<List<SubBoard>> = List(3) { List(3) { SubBoard() } },
    val nextRequiredRow: Int? = null,
    val nextRequiredCol: Int? = null,
    val globalWinner: Symbol = Symbol.NONE
)