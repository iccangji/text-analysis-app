package com.iccangji.understextapp.ui.screen.features.text_summarization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.iccangji.understextapp.UnderstextApplication
import com.iccangji.understextapp.data.TextAnalysisRepository
import com.iccangji.understextapp.data.UnderstextUiState
import com.iccangji.understextapp.data.model.SummarizedData
import com.iccangji.understextapp.data.model.TextSummarizationRequest
import com.iccangji.understextapp.data.utils.ErrorType
import com.iccangji.understextapp.data.utils.SummaryLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlin.math.roundToInt

class TextSummarizationViewModel(
    private val networkTextAnalysisRepository: TextAnalysisRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UnderstextUiState<SummarizedData>>(UnderstextUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun summarizeText(text: String, summaryLevel: SummaryLevel){
        viewModelScope.launch {
            _uiState.value = UnderstextUiState.Loading
            _uiState.value = try {
                if ( text != "" ) {
                    val summarizeResult = networkTextAnalysisRepository.summarizeText(
                        TextSummarizationRequest(
                            text = text,
                            summaryPercent = summaryLevel.value
                        )
                    )
                    val summarizedText = summarizeResult.sentences.joinToString(separator = "")
                    val percentage = (((text.length - summarizedText.length).toDouble() / text.length)*100).roundToInt()
                    if (summarizedText.isEmpty() || percentage==0){
                        UnderstextUiState.Error(ErrorType.TOO_SHORT_TO_SUMMARIZE)
                    }
                    else {
                        UnderstextUiState.Success(
                            SummarizedData(
                                summarizedText = summarizedText,
                                percentage = percentage
                            )
                        )
                    }
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
                TextSummarizationViewModel(application.container.networkRepository)
            }
        }
    }
}