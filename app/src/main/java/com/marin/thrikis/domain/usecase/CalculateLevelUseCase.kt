package com.marin.thrikis.domain.usecase

import com.marin.thrikis.domain.model.UserProfile

class CalculateLevelUseCase {

    fun addExperience(profile: UserProfile, xpGained: Int): UserProfile {
        var newXp = profile.currentXp + xpGained
        var newLevel = profile.level
        var newUnlockedIds = profile.unlockedSymbolsIds

        val xpRequiredForNextLevel = newLevel * 100

        while (newXp >= xpRequiredForNextLevel) {
            newXp -= xpRequiredForNextLevel
            newLevel++

            val nextSymbolId = newLevel
            if (!newUnlockedIds.contains(nextSymbolId)) {
                newUnlockedIds = newUnlockedIds + nextSymbolId
            }
        }

        return profile.copy(
            level = newLevel,
            currentXp = newXp,
            unlockedSymbolsIds = newUnlockedIds
        )
    }
}