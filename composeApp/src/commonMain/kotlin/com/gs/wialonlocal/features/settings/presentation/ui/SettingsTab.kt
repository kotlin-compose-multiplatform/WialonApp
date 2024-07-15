package com.gs.wialonlocal.features.settings.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object SettingsTab: Tab {
    @Composable
    override fun Content() {
        Text("Settings")
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 7U,
                title = strings.settings
            )
        }
}