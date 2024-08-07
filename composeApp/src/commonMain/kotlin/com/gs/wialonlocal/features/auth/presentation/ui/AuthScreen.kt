package com.gs.wialonlocal.features.auth.presentation.ui

import TestViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.core.constant.Constant
import com.gs.wialonlocal.features.auth.data.AuthSettings
import com.gs.wialonlocal.features.auth.presentation.viewmodel.AuthViewModel
import com.gs.wialonlocal.features.main.presentation.ui.MainScreen
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import kotlinx.coroutines.delay
import org.koin.compose.koinInject

class AuthScreen(
    private val onSuccess: () -> Unit
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AuthUI(onSuccess)
    }
}

@Composable
fun AuthUI(onSuccess: () -> Unit) {
    val webViewState =
        rememberWebViewState("${Constant.BASE_URL}/login.html?client_id=YTMerkezi&duration=2592000")
    val authSettings: AuthSettings = koinInject()
    val navigator = LocalNavigator.currentOrThrow
    LaunchedEffect(webViewState.lastLoadedUrl) {
        println("STATE: ${webViewState.lastLoadedUrl}")
        if("${webViewState.lastLoadedUrl}".contains("access_token")) {
            val token = "${webViewState.lastLoadedUrl}".split("access_token=")[1].split("&")[0]
            authSettings.saveToken(token)
            if(token.isNotEmpty()) {
                onSuccess()
            }
        }
    }
    Box(Modifier.fillMaxSize().background(
        color = Color.White
    ), contentAlignment = Alignment.Center) {
        if(webViewState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(35.dp), color = Color.Blue)
        }
        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize().alpha(
                if(webViewState.isLoading) 0f else 1f)
        )
    }
}