package com.iccangji.understextapp.ui.screen.features

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.iccangji.understextapp.R

@Composable
fun SentimentAnalysisScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier){
        Text(text = stringResource(id = R.string.sentiment_analysis))
    }
}