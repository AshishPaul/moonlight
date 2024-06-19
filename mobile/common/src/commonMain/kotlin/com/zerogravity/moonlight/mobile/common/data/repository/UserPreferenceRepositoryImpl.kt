package com.zerogravity.moonlight.mobile.common.data.repository

import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserPreferenceDataStore
import com.zerogravity.moonlight.mobile.common.domain.model.DarkThemeConfig
import com.zerogravity.moonlight.mobile.common.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

class UserPreferenceRepositoryImpl(
    private val userPreferenceDataStore: UserPreferenceDataStore
) : UserPreferenceRepository {

    override val userPreferences: Flow<UserPreferences> =
        userPreferenceDataStore.userData

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferenceDataStore.setDarkThemeConfig(darkThemeConfig)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferenceDataStore.setDynamicColorPreference(useDynamicColor)
    }
}