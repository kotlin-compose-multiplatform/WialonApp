package com.gs.wialonlocal.features.settings.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.components.BackFragment
import com.gs.wialonlocal.components.SwitchText

class FeedbackScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BackFragment(
            modifier = Modifier.fillMaxSize(),
            title = strings.feedback,
            onBackPresses = {
                navigator.pop()
            }
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    SwitchText(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings.feedback1,
                        arrow = true
                    )
                    SwitchText(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings.feedback2,
                        arrow = true
                    )
                    SwitchText(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings.feedback3,
                        arrow = true
                    )
                    SwitchText(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings.feedback4,
                        arrow = true
                    )
                    SwitchText(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings.feedback5,
                        arrow = true
                    )
                    SwitchText(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings.feedback6,
                        arrow = true
                    )
                }
                TextButton(
                    onClick = {

                    }
                ) {
                    Text(strings.viewDocs)
                }
            }
        }
    }
}