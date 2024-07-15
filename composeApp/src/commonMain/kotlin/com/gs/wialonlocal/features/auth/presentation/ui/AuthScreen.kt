package com.gs.wialonlocal.features.auth.presentation.ui

import TestViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.delay

class AuthScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.koinNavigatorScreenModel<TestViewModel>()
        AuthUI(viewModel)
    }
}

@Composable
fun AuthUI(viewModel: TestViewModel) {
    val webViewState =
        rememberWebViewState("https://gps.ytm.tm/login.html")
    Column(Modifier.fillMaxSize()) {
        val text = viewModel.state.collectAsState()

        LaunchedEffect(Unit) {
            delay(4000L)
            viewModel.setData("Hello")
        }
        Text(text.value)
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize()
        )
    }
}