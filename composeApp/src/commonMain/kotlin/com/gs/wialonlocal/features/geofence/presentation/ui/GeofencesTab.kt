package com.gs.wialonlocal.features.geofence.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object GeofencesTab: Tab {
    @Composable
    override fun Content() {
        Text("Geofences")
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 5U,
                title = strings.geofences
            )
        }
}