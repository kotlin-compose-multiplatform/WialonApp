package com.gs.wialonlocal.features.report.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.screen_rotation
import wialonlocal.composeapp.generated.resources.template
import wialonlocal.composeapp.generated.resources.unit

class ReportScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val openStart = remember {
            mutableStateOf(false)
        }

        val openOrientation = remember {
            mutableStateOf(false)
        }

        val openEnd = remember {
            mutableStateOf(false)
        }
        ReportDatePicker(
            title = "Start",
            open = openStart.value,
            onDismiss = {
                openStart.value = false
            }
        )

        ReportDatePicker(
            title = "End",
            open = openEnd.value,
            onDismiss = {
                openEnd.value = false
            }
        )

        OrientationDialog(
            open = openOrientation.value,
            onDismiss = {
                openOrientation.value = false
            }
        )
        Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            ToolBar(modifier = Modifier.fillMaxWidth()) {
                Box(Modifier.padding(16.dp)) {
                    Text(
                        text = strings.reports,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W500,
                            fontSize = 20.sp
                        )
                    )
                }
            }

            Column(Modifier.fillMaxWidth().weight(1f)) {
                ReportButton(
                    modifier = Modifier.fillMaxWidth().clickable {
                        navigator.push(TemplateSelect())
                    },
                    title = strings.template,
                    subTitle = strings.notSelected,
                    icon = painterResource(Res.drawable.template),
                    showArrow = true
                )
                ReportButton(
                    modifier = Modifier.fillMaxWidth().clickable {
                        navigator.push(ReportUnitSelect())
                    },
                    title = strings.unit,
                    subTitle = strings.notSelected,
                    icon = painterResource(Res.drawable.unit),
                    showArrow = true
                )

                Spacer(Modifier.height(16.dp))
                ReportDateRange(
                    modifier = Modifier.fillMaxWidth(),
                    startDate = "",
                    endDate = "",
                    onStartDateClick = {
                        openStart.value = true
                    },
                    onEndDateClick = {
                        openEnd.value = true
                    }
                )
                Spacer(Modifier.height(16.dp))
                ReportButton(
                    modifier = Modifier.fillMaxWidth().clickable {
                        openOrientation.value = true
                    },
                    title = strings.orientation,
                    subTitle = strings.portrail,
                    icon = painterResource(Res.drawable.screen_rotation),
                    showArrow = true
                )
            }

            Box(Modifier.padding(16.dp).fillMaxWidth()) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navigator.push(ReportView())
                    },
                    shape = RoundedCornerShape(2.dp)
                ) {
                    Text(
                        strings.executeReport,
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W500
                        )
                    )
                }
            }
        }

    }
}