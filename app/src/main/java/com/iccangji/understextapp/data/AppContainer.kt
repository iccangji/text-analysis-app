package com.iccangji.understextapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.iccangji.understextapp.data.network.UnderstextApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val networkRepository : NetworkTextAnalysisRepository
    val localRepository : LocalUserPreferencesRepository
}

class DefaultAppContainer(
    private val dataStore: DataStore<Preferences>,
) : AppContainer {
    private val baseUrl = "https://text-analysis12.p.rapidapi.com"
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()
    private val retrofitService: UnderstextApiService by lazy {
        retrofit.create(UnderstextApiService::class.java)
    }
    override val networkRepository: NetworkTextAnalysisRepository by lazy {
        NetworkTextAnalysisRepository(retrofitService)
    }
    override val localRepository: LocalUserPreferencesRepository by lazy {
        LocalUserPreferencesRepository(dataStore)
    }
}