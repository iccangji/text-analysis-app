package com.iccangji.understextapp.fake

import com.iccangji.understextapp.data.model.LanguageDetectionRequest
import com.iccangji.understextapp.data.model.LanguageDetectionResponse
import com.iccangji.understextapp.data.model.SentimentAnalysisRequest
import com.iccangji.understextapp.data.model.SentimentResponse
import com.iccangji.understextapp.data.model.TextSummarizationRequest
import com.iccangji.understextapp.data.model.TextSummarizationResponse
import com.iccangji.understextapp.data.network.UnderstextApiService

class FakeSentimentAnalysisApiService : UnderstextApiService{
    override suspend fun getSentimentData(data: SentimentAnalysisRequest): SentimentResponse {
        return FakeDataSource.fakeSentimentData
    }

    override suspend fun getSummarizeData(data: TextSummarizationRequest): TextSummarizationResponse {
        return FakeDataSource.fakeSummarizedData
    }

    override suspend fun getLanguageDetectionData(data: LanguageDetectionRequest): LanguageDetectionResponse {
        return FakeDataSource.fakeDetectionData
    }

}