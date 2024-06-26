package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.domain.model.DarkThemeConfig
import com.zerogravity.moonlight.mobile.common.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

    val userPreferences: Flow<UserPreferences>

    /**
     * Sets the desired dark theme config.
     */
    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    /**
     * Sets the preferred dynamic color config.
     */
    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)
}