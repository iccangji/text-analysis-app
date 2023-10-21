package com.iccangji.understextapp

import com.iccangji.understextapp.data.UnderstextUiState
import com.iccangji.understextapp.data.model.SentimentData
import com.iccangji.understextapp.fake.FakeDataSource
import com.iccangji.understextapp.fake.FakeNetworkRepository
import com.iccangji.understextapp.rules.TestDispatcherRule
import com.iccangji.understextapp.ui.screen.features.sentiment_analysis.SentimentViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Rule
import kotlin.math.roundToInt

class SentimentAnalysisViewModelTest {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun sentimentAnalysis_verifyUiStateSuccess() =
        runTest {
            val sentimentViewModel = SentimentViewModel(
                networkTextAnalysisRepository = FakeNetworkRepository()
            )
            sentimentViewModel.analyzeSentiment(
                "Test"
            )
            assertEquals(
                UnderstextUiState.Success(
                    SentimentData(
                        positiveValue = (FakeDataSource.fakeSentimentData.aggregateSentiment?.pos!! * 100).roundToInt(),
                        negativeValue = (FakeDataSource.fakeSentimentData.aggregateSentiment!!.neg!! * 100).roundToInt(),
                        neutralValue = (FakeDataSource.fakeSentimentData.aggregateSentiment!!.neu!! * 100).roundToInt(),
                    )
                ),
                sentimentViewModel.uiState.value
            )
        }
}