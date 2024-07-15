package com.gs.wialonlocal.features.status.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object StatusTab: Tab {
    @Composable
    override fun Content() {
        Text("Status")
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 6U,
                title = strings.status
            )
        }
}