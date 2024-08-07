package com.gs.wialonlocal.features.global.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.screenModule
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.features.auth.data.AuthSettings
import com.gs.wialonlocal.features.auth.presentation.ui.AuthScreen
import com.gs.wialonlocal.features.auth.presentation.viewmodel.AuthViewModel
import com.gs.wialonlocal.features.main.presentation.ui.MainScreen
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.car

class GlobalAuthScreen: Screen {
    @Composable
    override fun Content() {
        GlobalAuth()
    }

}

@Composable
fun GlobalAuth() {
    val navigator = LocalNavigator.currentOrThrow
    val authViewModel: AuthViewModel = navigator.koinNavigatorScreenModel()
    val sessionState = authViewModel.sessionState.collectAsState()

    fun auth() {
        authViewModel.auth(
            onRedirectLogin = {
                navigator.replace(AuthScreen(
                    onSuccess = {
                        navigator.replace(GlobalAuthScreen())
                    }
                ))
            },
            onSuccess = {

            }
        )
    }

    LaunchedEffect(true) {
        auth()
        while (true) {
            delay(5000L)
            if(sessionState.value.loading.not()) {
                auth()
            }
        }
    }

    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
        if(sessionState.value.checkIndex < 1L && sessionState.value.loading) {
            CircularProgressIndicator(modifier = Modifier.size(35.dp), color = MaterialTheme.colorScheme.onBackground)
        } else if(sessionState.value.error == AuthSettings.NO_SESSION_MESSAGE) {
            CircularProgressIndicator(modifier = Modifier.size(35.dp), color = MaterialTheme.colorScheme.onBackground)
        } else if(sessionState.value.session != null) {
            Navigator(MainScreen())
        } else {
            Image(
                painter = painterResource(Res.drawable.car),
                contentDescription = "logo",
                modifier = Modifier.size(120.dp)
            )
        }
    }
}