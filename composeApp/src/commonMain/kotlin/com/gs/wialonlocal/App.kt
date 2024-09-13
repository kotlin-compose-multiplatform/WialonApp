import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import cafe.adriel.lyricist.ProvideStrings
import cafe.adriel.lyricist.rememberStrings
import cafe.adriel.voyager.navigator.Navigator
import coil3.compose.rememberAsyncImagePainter
import com.gs.wialonlocal.core.network.provideHttpClient
import com.gs.wialonlocal.core.network.provideSettings
import com.gs.wialonlocal.core.network.provideViewModel
import com.gs.wialonlocal.features.auth.data.AuthSettings
import com.gs.wialonlocal.features.auth.di.authModule
import com.gs.wialonlocal.features.auth.presentation.ui.AuthScreen
import com.gs.wialonlocal.features.global.di.globalModule
import com.gs.wialonlocal.features.global.presentation.ui.GlobalAuthScreen
import com.gs.wialonlocal.features.main.presentation.ui.MainScreen
import com.gs.wialonlocal.features.monitoring.di.monitoringModule
import com.gs.wialonlocal.state.LocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinApplication
import com.gs.wialonlocal.theme.AppTheme
import org.koin.compose.koinInject
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.monitoring_active

@Composable
@Preview
fun App() {
    KoinApplication(
        application = {
            modules(
                provideHttpClient,
                provideViewModel,
                provideSettings,
                monitoringModule,
                authModule,
                globalModule
            )
        }
    ) {
        val authSettings = koinInject<AuthSettings>()

        LaunchedEffect(true) {
            authSettings.getToken()
        }

        val lyricist = rememberStrings()
        ProvideStrings(lyricist) {
            CompositionLocalProvider(
                LocalTheme provides rememberSaveable {
                    mutableStateOf(false)
                }
            ) {
                val theme = LocalTheme.current

                    AppTheme(
                        darkTheme = theme.value
                    ) {
                        Navigator(GlobalAuthScreen())
                    }

            }
        }
    }
}
