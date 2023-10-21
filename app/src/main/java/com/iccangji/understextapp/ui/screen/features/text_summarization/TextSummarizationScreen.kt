package com.iccangji.understextapp.ui.screen.features.text_summarization

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iccangji.understextapp.R
import com.iccangji.understextapp.data.UnderstextUiState
import com.iccangji.understextapp.data.utils.SummaryLevel
import com.iccangji.understextapp.ui.UnderstextScreen
import com.iccangji.understextapp.ui.components.BottomSheet
import com.iccangji.understextapp.ui.components.LanguageDropdownMenu
import com.iccangji.understextapp.ui.components.SummaryDropdownMenu
import com.iccangji.understextapp.ui.components.TextInput
import com.iccangji.understextapp.ui.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun TextSummarizationScreen(
    modifier: Modifier = Modifier,
    onClickMenu: () -> Unit,
    currentScreen: UnderstextScreen,
    summarizeViewModel : TextSummarizationViewModel = viewModel(
        factory = TextSummarizationViewModel.Factory
    )
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember{ SnackbarHostState() }
    val scrollState = rememberScrollState()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
    ){ padding ->

        var text by remember { mutableStateOf("") }
        var selectedSummaryLevel by remember { mutableStateOf(SummaryLevel.LOW) }
        val copyMessage = stringResource(id = R.string.copy_text)
        var showBottomSheet by remember{ mutableStateOf(false) }

        BoxWithConstraints(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ){
            val columnModifier = if (maxWidth > 600.dp) Modifier.verticalScroll(scrollState) else Modifier
            when (val uiState = summarizeViewModel.uiState.collectAsState().value) {
                is UnderstextUiState.Success -> {
                    SummarizeResult(
                        showMessage = {
                            scope.launch {
                                snackbarHostState.showSnackbar(copyMessage)
                            }
                        },
                        textValue = uiState.data.summarizedText,
                        percentage = uiState.data.percentage,
                        closeResult = {
                            summarizeViewModel.resetState()
                        }
                    )
                }
                else -> {
                    if (uiState is UnderstextUiState.Error) {
                        uiState.let{
                            val message = stringResource(id = it.error.message)
                            LaunchedEffect(Unit) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(message)
                                }
                            }
                        }
                        summarizeViewModel.resetState()
                    }

                    Column(
                        modifier = columnModifier
                            .fillMaxSize()
                    ) {
                        TopBar(
                            title = currentScreen.title,
                            onClickMenu = { onClickMenu() },
                            onClickInfo = {
                                showBottomSheet = true
                            }
                        )
                        if(uiState is UnderstextUiState.Loading){
                            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
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
                                    text = stringResource(R.string.summary_level),
                                    style = MaterialTheme.typography.labelMedium
                                )
                                SummaryDropdownMenu(
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    selectedOption = selectedSummaryLevel,
                                    onSelectOption = {
                                        selectedSummaryLevel = it
                                    }
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
                                    summarizeViewModel.summarizeText(
                                        text = text,
                                        summaryLevel = selectedSummaryLevel
                                    )
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
                title = R.string.text_summarization,
                desc = R.string.desc_text_summarization
            )
        }
    }
}

@Composable
fun SummarizeResult(
    modifier: Modifier = Modifier,
    showMessage: () -> Unit,
    closeResult: () -> Unit,
    textValue: String,
    percentage: Int
){
    BackHandler {
        closeResult()
    }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
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
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.summarized_text),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Justify
                    )
                    Text(
                        text = stringResource(R.string.up_to_percentage, percentage),
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Justify
                    )
                }
                IconButton(
                    onClick = {
                        copyTextButton(text = textValue, context = context)
                        showMessage()
                    }
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_content_copy_24),
                        contentDescription = stringResource(R.string.copy_text)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = textValue,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            )
        }
    }

}

fun copyTextButton(text: String, context: Context){
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    val clipData = ClipData.newPlainText("text", text)
    clipboardManager.setPrimaryClip(clipData)
}