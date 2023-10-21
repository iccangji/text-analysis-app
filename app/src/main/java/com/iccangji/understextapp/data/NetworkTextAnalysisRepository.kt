package com.iccangji.understextapp.data

import com.iccangji.understextapp.data.model.LanguageDetectionRequest
import com.iccangji.understextapp.data.model.LanguageDetectionResponse
import com.iccangji.understextapp.data.model.SentimentAnalysisRequest
import com.iccangji.understextapp.data.model.SentimentResponse
import com.iccangji.understextapp.data.model.TextSummarizationRequest
import com.iccangji.understextapp.data.model.TextSummarizationResponse
import com.iccangji.understextapp.data.network.UnderstextApiService
import com.iccangji.understextapp.data.utils.ErrorType

interface TextAnalysisRepository{
    suspend fun analyzeSentiment(
        data: SentimentAnalysisRequest
    ): SentimentResponse
    suspend fun summarizeText(
        data: TextSummarizationRequest
    ): TextSummarizationResponse
    suspend fun detectLanguage(
        data: LanguageDetectionRequest
    ): LanguageDetectionResponse
}
class NetworkTextAnalysisRepository(
    private val apiService: UnderstextApiService
) : TextAnalysisRepository {

    override suspend fun analyzeSentiment(data: SentimentAnalysisRequest): SentimentResponse = apiService.getSentimentData(data)

    override suspend fun summarizeText(data: TextSummarizationRequest): TextSummarizationResponse = apiService.getSummarizeData(data)

    override suspend fun detectLanguage(data: LanguageDetectionRequest): LanguageDetectionResponse = apiService.getLanguageDetectionData(data)

}

sealed class UnderstextUiState<out T> {
    data class Success<T>(val data: T) : UnderstextUiState<T>()
    data class Error(val error: ErrorType) : UnderstextUiState<Nothing>()
    object Loading : UnderstextUiState<Nothing>()
    object Initial : UnderstextUiState<Nothing>()
}