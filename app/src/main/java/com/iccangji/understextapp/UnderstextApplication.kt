package com.iccangji.understextapp

import android.app.Application
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.iccangji.understextapp.data.UserPreferencesRepository

private const val SHOW_ONBOARD_PREFERENCE_NAME = "show_onboard_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SHOW_ONBOARD_PREFERENCE_NAME
)

class UnderstextApplication: Application(){
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}