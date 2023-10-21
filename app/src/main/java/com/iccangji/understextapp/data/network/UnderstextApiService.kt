package com.iccangji.understextapp.data.network

import com.iccangji.understextapp.BuildConfig.API_KEY
import com.iccangji.understextapp.data.model.LanguageDetectionRequest
import com.iccangji.understextapp.data.model.LanguageDetectionResponse
import com.iccangji.understextapp.data.model.SentimentAnalysisRequest
import com.iccangji.understextapp.data.model.SentimentResponse
import com.iccangji.understextapp.data.model.TextSummarizationRequest
import com.iccangji.understextapp.data.model.TextSummarizationResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UnderstextApiService {
    @Headers(
        "X-RapidAPI-Key: $API_KEY",
        "X-RapidAPI-Host: text-analysis12.p.rapidapi.com"
    )
    @POST("sentiment-analysis/api/v1.1")
    suspend fun getSentimentData(
        @Body data: SentimentAnalysisRequest
    ): SentimentResponse
    @Headers(
        "X-RapidAPI-Key: $API_KEY",
        "X-RapidAPI-Host: text-analysis12.p.rapidapi.com"
    )
    @POST("summarize-text/api/v1.1")
    suspend fun getSummarizeData(
        @Body data: TextSummarizationRequest
    ): TextSummarizationResponse
    @Headers(
        "X-RapidAPI-Key: $API_KEY",
        "X-RapidAPI-Host: text-analysis12.p.rapidapi.com"
    )
    @POST("language-detection/api/v1.1")
    suspend fun getLanguageDetectionData(
        @Body data: LanguageDetectionRequest
    ): LanguageDetectionResponse
}
