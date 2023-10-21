package com.iccangji.understextapp.ui.screen.features.sentiment_analysis

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.iccangji.understextapp.UnderstextApplication
import com.iccangji.understextapp.data.TextAnalysisRepository
import com.iccangji.understextapp.data.UnderstextUiState
import com.iccangji.understextapp.data.model.SentimentAnalysisRequest
import com.iccangji.understextapp.data.model.SentimentData
import com.iccangji.understextapp.data.utils.ErrorType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt

class SentimentViewModel(
    private val networkTextAnalysisRepository: TextAnalysisRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UnderstextUiState<SentimentData>>(UnderstextUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun analyzeSentiment(text: String){
        viewModelScope.launch {
            _uiState.value = UnderstextUiState.Loading
            _uiState.value = try {
                if ( text != "" ) {
                    val sentimentResult = networkTextAnalysisRepository.analyzeSentiment(
                        SentimentAnalysisRequest(
                            text = text
                        )
                    )
                    UnderstextUiState.Success(
                        SentimentData(
                            positiveValue = (sentimentResult.aggregateSentiment?.pos!! * 100).roundToInt(),
                            negativeValue = (sentimentResult.aggregateSentiment.neg!! * 100).roundToInt(),
                            neutralValue = (sentimentResult.aggregateSentiment.neu!! * 100).roundToInt(),
                        )
                    )
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
                val application = (this[APPLICATION_KEY] as UnderstextApplication)
                SentimentViewModel(application.container.networkRepository)
            }
        }
    }
}