package com.marin.thrikis.domain.usecase

import com.marin.thrikis.domain.model.UserProfile

class CalculateLevelUseCase {

    private val xpBase = 100
    private val multiplier = 1.5

    fun getXpRequiredForLevel(level: Int): Long {
        if (level <= 1) return 0L
        return (xpBase * Math.pow(multiplier, (level - 1).toDouble())).toLong()
    }

    fun addXp(profile: UserProfile, xpGained: Long): UserProfile {
        var newXp = profile.xp + xpGained
        var newLevel = profile.level

        while (newLevel < 100 && newXp >= getXpRequiredForLevel(newLevel + 1)) {
            newLevel++
        }

        val updatedSymbols = profile.unlockedSymbols.toMutableList()
        val symbolToUnlock = newLevel / 2
        if (symbolToUnlock > 2 && symbolToUnlock <= 50 && !updatedSymbols.contains(symbolToUnlock)) {
            updatedSymbols.add(symbolToUnlock)
        }

        return profile.copy(
            level = newLevel,
            xp = newXp,
            unlockedSymbols = updatedSymbols
        )
    }
}