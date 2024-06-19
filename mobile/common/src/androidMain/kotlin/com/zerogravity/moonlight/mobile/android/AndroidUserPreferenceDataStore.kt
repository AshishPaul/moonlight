package com.zerogravity.moonlight.mobile.android

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.zerogravity.moonlight.mobile.common.data.local.datastore.UserPreferenceDataStore
import com.zerogravity.moonlight.mobile.common.domain.model.DarkThemeConfig
import com.zerogravity.moonlight.mobile.common.domain.model.UserPreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AndroidUserPreferenceDataStore(
    private val userPreferencesDataStore: DataStore<Preferences>,
    private val ioDispatcher: CoroutineDispatcher
) : UserPreferenceDataStore {

    private val dynamicColorPreferenceKey = booleanPreferencesKey("dynamic_color_preference")
    private val darkThemeConfigKey = intPreferencesKey("dark_theme_config")


    override val userData = userPreferencesDataStore.data.map { preferences ->
        val dynamicColorPreference = preferences[dynamicColorPreferenceKey] ?: false
        val darkThemeConfig = when (preferences[darkThemeConfigKey]) {
            1 -> DarkThemeConfig.LIGHT
            2 -> DarkThemeConfig.DARK
            else -> DarkThemeConfig.FOLLOW_SYSTEM
        }
        UserPreferences(darkThemeConfig, dynamicColorPreference)
    }.flowOn(ioDispatcher)

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        withContext(ioDispatcher) {
            userPreferencesDataStore.edit { settings ->
                settings[dynamicColorPreferenceKey] = useDynamicColor
            }
        }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {

        withContext(ioDispatcher) {
            userPreferencesDataStore.edit { settings ->
                settings[darkThemeConfigKey] = darkThemeConfig.ordinal
            }
        }
    }

    override suspend fun clear() {
        withContext(ioDispatcher) {
            userPreferencesDataStore.edit {
                it.clear()
            }
        }
    }
}