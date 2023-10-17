package com.iccangji.understextapp.ui.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iccangji.understextapp.R
import com.iccangji.understextapp.ui.UnderstextScreen
import com.iccangji.understextapp.ui.screen.features.ArticleExtractionScreen
import com.iccangji.understextapp.ui.screen.features.EntityRecognitionScreen
import com.iccangji.understextapp.ui.screen.features.LanguageDetectionScreen
import com.iccangji.understextapp.ui.screen.features.SentimentAnalysisScreen
import com.iccangji.understextapp.ui.screen.features.TextSummarizationScreen
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
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.language_detection))},
                    selected = currentScreen == UnderstextScreen.LanguageDetection,
                    onClick = {
                        if (currentScreen != UnderstextScreen.LanguageDetection) {
                            navController.navigate(UnderstextScreen.LanguageDetection.name)
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.text_summarization))},
                    selected = currentScreen == UnderstextScreen.TextSummarization,
                    onClick = {
                        if (currentScreen != UnderstextScreen.TextSummarization) {
                            navController.navigate(UnderstextScreen.TextSummarization.name)
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.article_extraction))},
                    selected = currentScreen == UnderstextScreen.ArticleExtraction,
                    onClick = {
                        if (currentScreen != UnderstextScreen.ArticleExtraction) {
                            navController.navigate(UnderstextScreen.ArticleExtraction.name)
                        }
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(id = R.string.entity_recognition))},
                    selected = currentScreen == UnderstextScreen.EntityRecognition,
                    onClick = {
                        if (currentScreen != UnderstextScreen.EntityRecognition) {
                            navController.navigate(UnderstextScreen.EntityRecognition.name)
                        }
                    }
                )
            }
        },
        gesturesEnabled = true,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 16.dp,
                    horizontal = 8.dp
                )
        ) {
            Box(
                contentAlignment = Alignment.CenterStart
            ) {
                IconButton(
                    onClick = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                            }
                        }
                    },
                    modifier = Modifier.height(32.dp)
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "")
                }
                Text(
                    text = stringResource(id = currentScreen.title),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            NavHost(
                navController = navController,
                startDestination = UnderstextScreen.SentimentAnalysis.name,
                modifier = modifier.padding(16.dp)
            ){
                composable(route = UnderstextScreen.SentimentAnalysis.name){
                    SentimentAnalysisScreen()
                }
                composable(route = UnderstextScreen.ArticleExtraction.name){
                    ArticleExtractionScreen()
                }
                composable(route = UnderstextScreen.EntityRecognition.name){
                    EntityRecognitionScreen()
                }
                composable(route = UnderstextScreen.LanguageDetection.name){
                    LanguageDetectionScreen()
                }
                composable(route = UnderstextScreen.TextSummarization.name){
                    TextSummarizationScreen()
                }
            }
        }
    }
}