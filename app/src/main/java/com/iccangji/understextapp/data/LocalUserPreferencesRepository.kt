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



interface UserPreferenceRepository{
    suspend fun savePreference(pref: Boolean)
    val showOnboard: Flow<OnboardResult<Boolean>>
}
class LocalUserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) : UserPreferenceRepository {
    private companion object{
        val SHOW_ONBOARD = booleanPreferencesKey("is_first_launch")
        const val TAG = "UserPreferencesRepo"
    }

    override val showOnboard: Flow<OnboardResult<Boolean>> = dataStore.data
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


    override suspend fun savePreference(pref: Boolean){
        dataStore.edit { preferences ->
            preferences[SHOW_ONBOARD] = pref
        }
    }
}


sealed class OnboardResult<out T>{
    data class ShowOnboard<T>(val show: T) : OnboardResult<T>()
    object Loading : OnboardResult<Nothing>()
}
