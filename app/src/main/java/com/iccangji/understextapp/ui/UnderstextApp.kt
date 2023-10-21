package com.iccangji.understextapp.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iccangji.understextapp.R
import com.iccangji.understextapp.data.OnboardResult
import com.iccangji.understextapp.ui.screen.HomeScreen
import com.iccangji.understextapp.ui.screen.OnboardScreen


enum class UnderstextScreen(@StringRes val title: Int){
    Start(title = R.string.app_name),
    EntityRecognition(title = R.string.entity_recognition),
    LanguageDetection(title = R.string.language_detection),
    SentimentAnalysis(title = R.string.sentiment_analysis),
    TextSummarization(title = R.string.text_summarization),
}

@Composable
fun UnderstextApp(
    modifier: Modifier = Modifier,
    appViewModel: UnderstextViewModel = viewModel(
        factory = UnderstextViewModel.Factory
    )
) {

    when (val onboard = appViewModel.showOnboard.collectAsState().value) {
        is OnboardResult.ShowOnboard -> {
            if (onboard.show) {
                OnboardScreen(
                    modifier = modifier,
                    setOnBoard = appViewModel::setShowOnboard,
                )
            } else {
                HomeScreen()
            }
        }
        else -> {}
    }
}