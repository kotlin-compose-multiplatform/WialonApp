package com.gs.wialonlocal.state

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import com.gs.wialonlocal.features.settings.data.settings.AppTheme
import com.gs.wialonlocal.features.settings.data.settings.MapType

data class AppSettings(
    val mapType: MapType = MapType.MAP, // hybrid, satellite
    val language: String = "tm", // ru, en
    val theme: AppTheme = AppTheme.SYSTEM, // dark,light
)

val LocalAppSettings = compositionLocalOf {
    mutableStateOf(AppSettings())
}