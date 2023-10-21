package com.iccangji.understextapp.fake

import com.iccangji.understextapp.data.model.AggregateSentiment
import com.iccangji.understextapp.data.model.LanguageDetectionResponse
import com.iccangji.understextapp.data.model.SentimentResponse
import com.iccangji.understextapp.data.model.TextSummarizationResponse

object FakeDataSource {
    val fakeSentimentData = SentimentResponse(
        sentiment = null,
        sentimentList = null,
        appVersion = null,
        msg = null,
        ok = null,
        timeTaken = null,
        aggregateSentiment = AggregateSentiment(
            neg = 0.1231237868172,
            pos = 0.812376817263,
            neu = 0.0012838312312,
            compound = 0.0,
        )
    )

    val fakeDetectionData = LanguageDetectionResponse(
        appVersion = "v1.1",
        timeTaken = 0.0024428367614746094,
        msg = "language detection successful",
        ok = true,
        languageProbability = mapOf(
            "en" to 0.999997998549039
        )
    )

    val fakeSummarizedData = TextSummarizationResponse(
        appVersion = "v1.1",
        timeTaken = 0.04989957809448242,
        msg = "summarization successful",
        ok = true,
        sentenceCount = 8,
        summary = "It is the first documented work in Mathematics that used a series of numbered chunks to break down the solution to a problem. His name roughly translates to Algoritmi, in Latin, and he develops a technique called Algorism. Pascal was a French Mathematician and at an early age he pioneered the field of calculating machines. It incorporated an Arithmetic and Logic unit ALU , integrated memory, and control flow statements. 1900s In the early 1900s, Bertrand Russell invented type theory to avoid paradoxes in a variety of formal logics. Lambda calculus introduced a new way of viewing problems in Mathematics, and inspired a large number of programming languages. He formalized a lot of concepts in theoretical Computer Science with his work on the Turing Machine. Apart from his research, he was a codebreaker at Bletchley Park during WW2, and was deeply interested in mathematical biology theory.",
        sentences = listOf(
            "It is the first documented work in Mathematics that used a series of numbered chunks to break down the solution to a problem.",
            "His name roughly translates to Algoritmi, in Latin, and he develops a technique called Algorism.",
            "Pascal was a French Mathematician and at an early age he pioneered the field of calculating machines.",
            "It incorporated an Arithmetic and Logic unit ALU , integrated memory, and control flow statements.",
            "1900s In the early 1900s, Bertrand Russell invented type theory to avoid paradoxes in a variety of formal logics.",
            "Lambda calculus introduced a new way of viewing problems in Mathematics, and inspired a large number of programming languages.",
            "He formalized a lot of concepts in theoretical Computer Science with his work on the Turing Machine.",
            "Apart from his research, he was a codebreaker at Bletchley Park during WW2, and was deeply interested in mathematical biology theory."
        )
    )

}