package com.iccangji.understextapp.fake

import com.iccangji.understextapp.data.TextAnalysisRepository
import com.iccangji.understextapp.data.model.LanguageDetectionRequest
import com.iccangji.understextapp.data.model.LanguageDetectionResponse
import com.iccangji.understextapp.data.model.SentimentAnalysisRequest
import com.iccangji.understextapp.data.model.SentimentResponse
import com.iccangji.understextapp.data.model.TextSummarizationRequest
import com.iccangji.understextapp.data.model.TextSummarizationResponse

class FakeNetworkRepository : TextAnalysisRepository{

    override suspend fun analyzeSentiment(data: SentimentAnalysisRequest): SentimentResponse {
        return FakeDataSource.fakeSentimentData
    }

    override suspend fun summarizeText(data: TextSummarizationRequest): TextSummarizationResponse {
        return FakeDataSource.fakeSummarizedData
    }

    override suspend fun detectLanguage(data: LanguageDetectionRequest): LanguageDetectionResponse {
        return FakeDataSource.fakeDetectionData
    }
}