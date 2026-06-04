package com.marin.thrikis.domain.model

data class SubBoard(
    val cells: List<List<Symbol>> = List(3) { List(3) { Symbol.NONE } },
    val wonBy: Symbol = Symbol.NONE
)