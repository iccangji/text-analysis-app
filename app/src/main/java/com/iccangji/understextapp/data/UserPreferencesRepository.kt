package com.iccangji.understextapp.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object{
        val SHOW_ONBOARD = booleanPreferencesKey("is_first_launch")
        const val TAG = "UserPreferencesRepo"
    }

    val showOnboard: Flow<OnboardResult<Boolean>> = dataStore.data
        .catch {
            if (it is IOException){
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            OnboardResult.ShowOnboard(preferences[SHOW_ONBOARD] ?: true)
        }

    suspend fun savePreference(isFirstLaunch: Boolean){
        dataStore.edit { preferences ->
            preferences[SHOW_ONBOARD] = isFirstLaunch
        }
    }
}

sealed class OnboardResult<out T>{
    data class ShowOnboard<T>(val show: T) : OnboardResult<T>()
    object Loading : OnboardResult<Nothing>()
}
