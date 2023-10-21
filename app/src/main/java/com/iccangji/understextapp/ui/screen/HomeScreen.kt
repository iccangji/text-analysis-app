package com.iccangji.understextapp.ui.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iccangji.understextapp.R
import com.iccangji.understextapp.ui.UnderstextScreen
import com.iccangji.understextapp.ui.screen.features.language_detection.LanguageDetectionScreen
import com.iccangji.understextapp.ui.screen.features.sentiment_analysis.SentimentAnalysisScreen
import com.iccangji.understextapp.ui.screen.features.text_summarization.TextSummarizationScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    BackHandler {
        (context as Activity).finish()
    }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = UnderstextScreen.valueOf(
        backStackEntry?.destination?.route ?: UnderstextScreen.Start.name
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                windowInsets = WindowInsets(
                    top = 16.dp,
                    bottom = 16.dp,
                    left = 16.dp,
                    right = 16.dp,
                )
            ){
                Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(16.dp))
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.sentiment_analysis))},
                    selected = currentScreen == UnderstextScreen.SentimentAnalysis,
                    onClick = {
                        if (currentScreen != UnderstextScreen.SentimentAnalysis) {
                            navController.navigate(UnderstextScreen.SentimentAnalysis.name)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.text_summarization))},
                    selected = currentScreen == UnderstextScreen.TextSummarization,
                    onClick = {
                        if (currentScreen != UnderstextScreen.TextSummarization) {
                            navController.navigate(UnderstextScreen.TextSummarization.name)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.language_detection))},
                    selected = currentScreen == UnderstextScreen.LanguageDetection,
                    onClick = {
                        if (currentScreen != UnderstextScreen.LanguageDetection) {
                            navController.navigate(UnderstextScreen.LanguageDetection.name)
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    }
                )
            }
        },
        gesturesEnabled = true,
    ) {
        NavHost(
            navController = navController,
            startDestination = UnderstextScreen.SentimentAnalysis.name,
        ){
            composable(route = UnderstextScreen.SentimentAnalysis.name){
                SentimentAnalysisScreen(
                    onClickMenu = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    currentScreen = UnderstextScreen.SentimentAnalysis
                )
            }
            composable(route = UnderstextScreen.LanguageDetection.name){
                LanguageDetectionScreen(
                    onClickMenu = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    currentScreen = UnderstextScreen.LanguageDetection
                )
            }
            composable(route = UnderstextScreen.TextSummarization.name){
                TextSummarizationScreen(
                    onClickMenu = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    currentScreen = UnderstextScreen.TextSummarization
                )
            }
        }
    }
}