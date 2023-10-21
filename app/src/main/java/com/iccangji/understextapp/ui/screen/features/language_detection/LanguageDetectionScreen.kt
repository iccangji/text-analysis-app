package com.iccangji.understextapp.ui.screen.features.language_detection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.iccangji.understextapp.R
import com.iccangji.understextapp.data.UnderstextUiState
import com.iccangji.understextapp.ui.UnderstextScreen
import com.iccangji.understextapp.ui.components.BottomSheet
import com.iccangji.understextapp.ui.components.TextInput
import com.iccangji.understextapp.ui.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun LanguageDetectionScreen(
    modifier: Modifier = Modifier,
    onClickMenu: () -> Unit,
    currentScreen: UnderstextScreen,
    languageDetectionViewModel : LanguageDetectionViewModel = viewModel(
        factory = LanguageDetectionViewModel.Factory
    )
) {
    val snackbarHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ){ padding ->
        var showBottomSheet by remember{ mutableStateOf(false) }
        var text by remember { mutableStateOf("") }
        val uiState = languageDetectionViewModel.uiState.collectAsState().value

        BoxWithConstraints(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
        ){
            val columnModifier = if (maxWidth > 600.dp) Modifier.verticalScroll(scrollState) else Modifier
            Column(
                modifier = columnModifier
                    .padding(padding)
            ) {

                if (uiState is UnderstextUiState.Error) {
                    uiState.let {
                        val message = stringResource(id = it.error.message)
                        LaunchedEffect(Unit) {
                            scope.launch {
                                snackbarHostState.showSnackbar(message)
                            }
                        }
                    }
                    languageDetectionViewModel.resetState()
                }

                TopBar(
                    title = currentScreen.title,
                    onClickMenu = { onClickMenu() },
                    onClickInfo = {
                        showBottomSheet = true
                    }
                )
                if (uiState is UnderstextUiState.Loading) {
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
                            text = stringResource(R.string.text),
                            style = MaterialTheme.typography.labelMedium
                        )
                        TextInput(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            onTextChange = { text = it },
                            textValue = text
                        )
                        if (uiState is UnderstextUiState.Success) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 16.dp, bottom = 8.dp),
                                text = stringResource(R.string.detected_language),
                                style = MaterialTheme.typography.labelMedium,
                            )
                            LazyColumn {
                                items(uiState.data){ item ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        AsyncImage(
                                            model = item.imageUrl,
                                            contentDescription = null,
                                        )
                                        Column(
                                            modifier.padding(start = 8.dp)
                                        ) {
                                            Text(text = item.percentage)
                                            Text(text = item.name)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Button(
                        onClick = {
                            languageDetectionViewModel.detectLanguage(
                                text = text
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

        if (showBottomSheet) {
            BottomSheet(
                dismissRequest = {
                    showBottomSheet = false
                },
                title = R.string.language_detection,
                desc = R.string.identify_the_language_of_text
            )
        }
    }
}