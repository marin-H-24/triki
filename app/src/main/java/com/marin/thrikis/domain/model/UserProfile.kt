package com.marin.thrikis.domain.model

data class UserProfile(
    val id: String,
    val name: String,
    val email: String,
    val level: Int = 1,
    val xp: Long = 0L,
    val wins: Int = 0,
    val currentStreak: Int = 0,
    val highestStreak: Int = 0,
    val unlockedSymbols: List<Int> = listOf(1, 2)
)