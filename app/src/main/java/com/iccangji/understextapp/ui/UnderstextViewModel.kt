package com.iccangji.understextapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.iccangji.understextapp.UnderstextApplication
import com.iccangji.understextapp.data.OnboardResult
import com.iccangji.understextapp.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UnderstextViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    val showOnboard: StateFlow<OnboardResult<Boolean>> =
        userPreferencesRepository.showOnboard.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = OnboardResult.Loading
        )

    fun setShowOnboard(){
        viewModelScope.launch {
            userPreferencesRepository.savePreference(false)
        }
    }
    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UnderstextApplication)
                UnderstextViewModel(application.userPreferencesRepository)
            }
        }
    }

}