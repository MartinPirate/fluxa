package dev.fluxa.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Typed key-value persistence backed by DataStore.
 * Replaces SharedPreferences with a coroutine-friendly API.
 */
class FluxaPreferences(
    private val dataStore: DataStore<Preferences>,
) {
    // --- String ---
    fun stringFlow(key: String, default: String = ""): Flow<String> =
        dataStore.data.map { it[stringPreferencesKey(key)] ?: default }

    suspend fun getString(key: String, default: String = ""): String =
        stringFlow(key, default).first()

    suspend fun setString(key: String, value: String) {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    // --- Int ---
    fun intFlow(key: String, default: Int = 0): Flow<Int> =
        dataStore.data.map { it[intPreferencesKey(key)] ?: default }

    suspend fun getInt(key: String, default: Int = 0): Int =
        intFlow(key, default).first()

    suspend fun setInt(key: String, value: Int) {
        dataStore.edit { it[intPreferencesKey(key)] = value }
    }

    // --- Long ---
    fun longFlow(key: String, default: Long = 0L): Flow<Long> =
        dataStore.data.map { it[longPreferencesKey(key)] ?: default }

    suspend fun getLong(key: String, default: Long = 0L): Long =
        longFlow(key, default).first()

    suspend fun setLong(key: String, value: Long) {
        dataStore.edit { it[longPreferencesKey(key)] = value }
    }

    // --- Boolean ---
    fun booleanFlow(key: String, default: Boolean = false): Flow<Boolean> =
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: default }

    suspend fun getBoolean(key: String, default: Boolean = false): Boolean =
        booleanFlow(key, default).first()

    suspend fun setBoolean(key: String, value: Boolean) {
        dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    // --- Remove ---
    suspend fun remove(key: String) {
        dataStore.edit { prefs ->
            listOf(
                stringPreferencesKey(key),
                intPreferencesKey(key),
                longPreferencesKey(key),
                booleanPreferencesKey(key),
            ).forEach { prefs.remove(it) }
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}

private val Context.fluxaDataStore: DataStore<Preferences> by preferencesDataStore(name = "fluxa_prefs")

/**
 * Create a FluxaPreferences instance from a Context.
 */
fun Context.fluxaPreferences(): FluxaPreferences = FluxaPreferences(fluxaDataStore)
