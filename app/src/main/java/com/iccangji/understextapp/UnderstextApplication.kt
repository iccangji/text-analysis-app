package com.iccangji.understextapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.iccangji.understextapp.data.AppContainer
import com.iccangji.understextapp.data.DefaultAppContainer

private const val SHOW_ONBOARD_PREFERENCE_NAME = "show_onboard_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SHOW_ONBOARD_PREFERENCE_NAME
)

class UnderstextApplication: Application(){
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(dataStore)
    }
}