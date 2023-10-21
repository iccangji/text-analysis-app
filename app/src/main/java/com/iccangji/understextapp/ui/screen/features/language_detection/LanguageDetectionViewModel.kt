package com.iccangji.understextapp.ui.screen.features.language_detection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.iccangji.understextapp.UnderstextApplication
import com.iccangji.understextapp.data.TextAnalysisRepository
import com.iccangji.understextapp.data.UnderstextUiState
import com.iccangji.understextapp.data.model.LanguageData
import com.iccangji.understextapp.data.model.LanguageDetectionRequest
import com.iccangji.understextapp.data.utils.ErrorType
import com.iccangji.understextapp.data.utils.countryMap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt

class LanguageDetectionViewModel(
    private val networkTextAnalysisRepository: TextAnalysisRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UnderstextUiState<List<LanguageData>>>(UnderstextUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun detectLanguage(text: String){
        viewModelScope.launch {
            _uiState.value = UnderstextUiState.Loading
            _uiState.value = try {
                if ( text != "" ) {
                    val detectionResult = networkTextAnalysisRepository.detectLanguage(
                        LanguageDetectionRequest(text)
                    )
                    val result = detectionResult.languageProbability.toList().map { (key, value) ->
                        LanguageData(
                            name = countryMap.getValue(key).name,
                            imageUrl = countryMap.getValue(key).imageUrl!!,
                            percentage =  "${(value*100).roundToInt()}%"
                        )
                    }

                    UnderstextUiState.Success(result)
                } else {
                    UnderstextUiState.Error(ErrorType.NO_TEXT)
                }
            } catch (e: IOException) {
                UnderstextUiState.Error(ErrorType.NO_INTERNET)
            } catch (e: HttpException) {
                UnderstextUiState.Error(ErrorType.NO_INTERNET)
            }
        }
    }

    fun resetState(){
        _uiState.value = UnderstextUiState.Initial
    }

    companion object{
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as UnderstextApplication
                LanguageDetectionViewModel(application.container.networkRepository)
            }
        }
    }
}