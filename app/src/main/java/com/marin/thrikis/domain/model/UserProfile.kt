package com.marin.thrikis.domain.model

data class UserProfile(
    val name: String = "Jugador",
    val level: Int = 1,
    val currentXp: Int = 0,
    val equippedSymbolId: Int = Symbol.PLAYER_ONE_BASE.id,
    val unlockedSymbolsIds: List<Int> = listOf(Symbol.PLAYER_ONE_BASE.id, Symbol.PLAYER_TWO_BASE.id)
)