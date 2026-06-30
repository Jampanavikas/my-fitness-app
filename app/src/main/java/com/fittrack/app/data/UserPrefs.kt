package com.fittrack.app.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "fittrack_prefs")

/** Lightweight key-value store for per-day water count and settings. */
class UserPrefs(private val context: Context) {

    private fun waterKey(epochDay: Long) = intPreferencesKey("water_$epochDay")
    private val remindersKey = booleanPreferencesKey("reminders_enabled")

    fun waterGlasses(epochDay: Long): Flow<Int> =
        context.dataStore.data.map { it[waterKey(epochDay)] ?: 0 }

    suspend fun setWater(epochDay: Long, glasses: Int) {
        context.dataStore.edit { it[waterKey(epochDay)] = glasses.coerceIn(0, 30) }
    }

    val remindersEnabled: Flow<Boolean> =
        context.dataStore.data.map { it[remindersKey] ?: true }

    suspend fun remindersEnabledOnce(): Boolean =
        context.dataStore.data.map { it[remindersKey] ?: true }.first()

    suspend fun setRemindersEnabled(enabled: Boolean) {
        context.dataStore.edit { it[remindersKey] = enabled }
    }
}
