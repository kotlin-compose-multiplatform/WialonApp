package com.gs.wialonlocal.features.report.presentation.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object ReportTab: Tab {
    @Composable
    override fun Content() {
        Text("Report")
    }

    override val options: TabOptions
        @Composable
        get() {
            return TabOptions(
                index = 3U,
                title = strings.reports
            )
        }
}