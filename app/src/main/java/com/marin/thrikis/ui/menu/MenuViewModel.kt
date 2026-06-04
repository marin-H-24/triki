package com.marin.thrikis.ui.menu

import androidx.lifecycle.ViewModel
import com.marin.thrikis.domain.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel : ViewModel() {

    private val _userProfile = MutableStateFlow(
        UserProfile(
            name = "Santiago",
            level = 12,
            currentXp = 850
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

}