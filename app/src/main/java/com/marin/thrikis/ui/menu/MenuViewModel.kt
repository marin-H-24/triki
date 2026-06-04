package com.marin.thrikis.ui.menu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.marin.thrikis.domain.model.UserProfile

class MenuViewModel : ViewModel() {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        _userProfile.value = UserProfile(
            id = "THK-8962",
            name = "Santiago",
            email = "santiago@mail.com",
            level = 12,
            xp = 1450L,
            wins = 24,
            currentStreak = 3
        )
    }
}