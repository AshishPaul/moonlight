package com.zerogravity.moonlight.mobile.common.data.local.datastore

import com.zerogravity.moonlight.mobile.common.domain.model.DarkThemeConfig
import com.zerogravity.moonlight.mobile.common.domain.model.UserPreferences
import com.zerogravity.moonlight.shared.domain.model.response.Tokens
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataStore {

    val userData: Flow<UserPreferences>

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun clear()

}