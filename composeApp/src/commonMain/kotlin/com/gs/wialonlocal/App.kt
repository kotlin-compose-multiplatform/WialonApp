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
import com.gs.wialonlocal.features.geofence.di.geoFenceModule
import com.gs.wialonlocal.features.global.di.globalModule
import com.gs.wialonlocal.features.global.presentation.ui.GlobalAuthScreen
import com.gs.wialonlocal.features.main.presentation.ui.MainScreen
import com.gs.wialonlocal.features.monitoring.di.monitoringModule
import com.gs.wialonlocal.features.report.di.reportModule
import com.gs.wialonlocal.features.settings.data.settings.AppSettings
import com.gs.wialonlocal.features.settings.data.settings.AppTheme
import com.gs.wialonlocal.features.settings.di.settingsModule
import com.gs.wialonlocal.state.LocalAppSettings
import com.gs.wialonlocal.state.LocalTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinApplication
import com.gs.wialonlocal.theme.AppTheme
import org.koin.compose.koinInject
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.monitoring_active

@Composable
@Preview
fun App(
    context: Any? = null
) {
    KoinApplication(
        application = {
            modules(
                provideHttpClient,
                provideViewModel,
                provideSettings,
                monitoringModule,
                authModule,
                globalModule,
                reportModule,
                geoFenceModule,
                settingsModule
            )
        }
    ) {
        val authSettings = koinInject<AuthSettings>()

        LaunchedEffect(true) {
            authSettings.getToken()
        }

        val settings = koinInject<AppSettings>()

        val isSystemDark = isSystemInDarkTheme()


        val localSettings = remember {
            mutableStateOf(com.gs.wialonlocal.state.AppSettings(
                mapType = settings.getMapType(),
                language = settings.getLanguage(),
                theme = settings.getTheme()
            ))
        }

        val lyricist = rememberStrings()
        ProvideStrings(lyricist) {
            CompositionLocalProvider(
                LocalTheme provides rememberSaveable {
                    mutableStateOf(settings.getTheme()==AppTheme.DARK || isSystemDark)
                },
                LocalAppSettings provides remember {
                    mutableStateOf(localSettings.value)
                }
            ) {
                val theme = LocalTheme.current

                val localSettingsInside = LocalAppSettings.current

                LaunchedEffect(localSettingsInside.value.language) {
                    lyricist.languageTag = localSettingsInside.value.language
                }

                    AppTheme(
                        darkTheme = theme.value
                    ) {
                        Navigator(GlobalAuthScreen())
                    }

            }
        }
    }
}
