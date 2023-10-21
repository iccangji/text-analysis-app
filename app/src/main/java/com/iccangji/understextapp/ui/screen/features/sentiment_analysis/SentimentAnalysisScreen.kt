package com.iccangji.understextapp.ui.screen.features.sentiment_analysis

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iccangji.understextapp.R
import com.iccangji.understextapp.data.UnderstextUiState
import com.iccangji.understextapp.data.model.SentimentData
import com.iccangji.understextapp.ui.UnderstextScreen
import com.iccangji.understextapp.ui.components.BottomSheet
import com.iccangji.understextapp.ui.components.LanguageDropdownMenu
import com.iccangji.understextapp.ui.components.SentimentCard
import com.iccangji.understextapp.ui.components.TextInput
import com.iccangji.understextapp.ui.components.TopBar
import com.iccangji.understextapp.ui.theme.AppTheme
import kotlinx.coroutines.launch

@Composable
fun SentimentAnalysisScreen(
    modifier: Modifier = Modifier,
    onClickMenu: () -> Unit,
    currentScreen: UnderstextScreen,
    sentimentViewModel: SentimentViewModel = viewModel(
        factory = SentimentViewModel.Factory
    )
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember{ SnackbarHostState() }
    val scrollState = rememberScrollState()


    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ){ padding ->
        var showBottomSheet by remember{ mutableStateOf(false) }
        var text by remember { mutableStateOf("") }

        BoxWithConstraints(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ){
            val columnModifier = if (maxWidth > 600.dp) Modifier.verticalScroll(scrollState) else Modifier
            when (val uiState = sentimentViewModel.uiState.collectAsState().value) {
                is UnderstextUiState.Success -> {
                    SentimentResult(
                        modifier = Modifier.padding(vertical = 16.dp),
                        closeResult = {
                            sentimentViewModel.resetState()
                        },
                        result = uiState.data
                    )
                }

                else -> {
                    if ( uiState is UnderstextUiState.Error ) {
                        uiState.let{
                            val message = stringResource(id = it.error.message)
                            LaunchedEffect(Unit) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(message)
                                }
                            }
                        }
                        sentimentViewModel.resetState()
                    }

                    Column(
                        modifier = columnModifier
                            .fillMaxSize(),
                    ) {
                        TopBar(
                            title = currentScreen.title,
                            onClickMenu = { onClickMenu() },
                            onClickInfo = {
                                showBottomSheet = true
                            }
                        )
                        if( uiState is UnderstextUiState.Loading){
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .fillMaxHeight()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = stringResource(R.string.language),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                LanguageDropdownMenu(
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(R.string.text),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                TextInput(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp),
                                    onTextChange = { text = it },
                                    textValue = text,
                                )
                            }
                            Button(
                                onClick = {
                                    scope.launch{
                                        scrollState.animateScrollTo(0)
                                    }
                                    sentimentViewModel.analyzeSentiment(text)
                                },
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(text = stringResource(R.string.analyze))
                            }
                        }
                    }
                }
            }
        }
        if (showBottomSheet) {
            BottomSheet(
                dismissRequest = {
                    showBottomSheet = false
                },
                title = R.string.sentiment_analysis,
                desc = R.string.desc_sentiment_analysis
            )
        }
    }
}

@Composable
fun SentimentResult(
    modifier: Modifier = Modifier,
    closeResult : () -> Unit,
    result: SentimentData
){
    BackHandler {
        closeResult()
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
        IconButton(onClick = { closeResult() }) {
            Icon(
                Icons.Default.Close,
                contentDescription = ""
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                SentimentCard(
                    value = result.positiveValue,
                    title = R.string.positive,
                    image = R.drawable.positive_face
                )
                Spacer(modifier = Modifier.width(16.dp))
                SentimentCard(
                    value = result.negativeValue,
                    title = R.string.negative,
                    image = R.drawable.negative_face
                )
                BoxWithConstraints(
                    modifier = Modifier.padding(0.dp)
                ) {
                    if(maxWidth >= 400.dp) {
                        SentimentCard(
                            value = result.neutralValue,
                            title = R.string.neutral,
                            image = R.drawable.neutral_face,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            BoxWithConstraints {
                if (maxWidth < 600.dp) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        SentimentCard(
                            value = result.neutralValue,
                            title = R.string.neutral,
                            image = R.drawable.neutral_face,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.DESKTOP)
@Composable
fun SentimentAnalysisPreview(){
    AppTheme {
        SentimentAnalysisScreen(
            currentScreen = UnderstextScreen.SentimentAnalysis,
            onClickMenu = {}
        )
    }
}