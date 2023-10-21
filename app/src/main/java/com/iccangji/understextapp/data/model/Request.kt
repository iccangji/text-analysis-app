package com.iccangji.understextapp.data.model

import com.google.gson.annotations.SerializedName
import com.iccangji.understextapp.data.utils.SummaryLevel

data class SentimentAnalysisRequest(
    @field:SerializedName("language")
    var language: String = "english",
    @field:SerializedName("text")
    var text: String
)

data class LanguageDetectionRequest(
    @field:SerializedName("text")
    var text: String
)

data class TextSummarizationRequest(
    @field:SerializedName("language")
    var language: String = "english",
    @field:SerializedName("summary_percent")
    var summaryPercent: Int = SummaryLevel.LOW.value,
    @field:SerializedName("text")
    var text: String
)