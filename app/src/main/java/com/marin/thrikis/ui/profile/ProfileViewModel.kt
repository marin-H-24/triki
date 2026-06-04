package com.marin.thrikis.ui.profile

import androidx.lifecycle.ViewModel
import com.marin.thrikis.domain.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel : ViewModel() {

    private val _profileState = MutableStateFlow(
        UserProfile(
            name = "Santiago",
            level = 12,
            currentXp = 850
        )
    )
    val profileState: StateFlow<UserProfile> = _profileState.asStateFlow()

    fun getXpForNextLevel(level: Int): Int {
        return level * 100
    }

    fun equipSymbol(symbolId: Int) {
        val currentProfile = _profileState.value
        if (currentProfile.unlockedSymbolsIds.contains(symbolId)) {
            _profileState.value = currentProfile.copy(equippedSymbolId = symbolId)
        }
    }
}