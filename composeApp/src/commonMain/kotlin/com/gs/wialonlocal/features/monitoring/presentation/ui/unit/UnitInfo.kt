package com.gs.wialonlocal.features.monitoring.presentation.ui.unit

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.features.monitoring.data.entity.history.findParam
import com.gs.wialonlocal.features.monitoring.data.entity.history.findParamValue
import com.gs.wialonlocal.features.monitoring.presentation.ui.settings.InfoTabSettings
import com.gs.wialonlocal.features.monitoring.presentation.viewmodel.MonitoringViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.settings_active

@Composable
fun UnitInfo(modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel = navigator.koinNavigatorScreenModel<MonitoringViewModel>()
    val fieldState = viewModel.fieldState.collectAsState()
    Column(
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        if (fieldState.value.loading) {
            LinearProgressIndicator(Modifier.fillMaxWidth())
        } else if (fieldState.value.data != null) {
            fieldState.value.data?.let { list ->
                list.d?.flds?.let { fields ->
                    if (fields.values.isNotEmpty()) {
                        InfoAccordion(
                            title = "Custom fields",
                            items = fields.values.mapIndexed { index, flds ->
                                InfoItem(flds.n ?: "Name", flds.v ?: "")
                            }
                        )
                    }

                }

                list.d?.sens?.let { sensors ->
                    if (sensors.values.isNotEmpty()) {
                        InfoAccordion(
                            title = "Sensors",
                            items = sensors.values.mapIndexed { index, sens ->
                                println(sens)
                                InfoItem(
                                    sens.n ?: "Name",
                                    findParamValue(
                                        sens.p ?: "posinfo",
                                        list.d.prms,
                                        sens.m ?: "v"
                                    ).joinToString(",")
                                )
                            }
                        )
                    }
                }

                list.d?.pflds?.let {profiles->
                    if(profiles.values.isNotEmpty()) {
                        InfoAccordion(
                            title = "Profiles",
                            items = profiles.values.mapIndexed { index, pflds ->
                                InfoItem(pflds.n ?: "Name", pflds.v ?: "")
                            }
                        )
                    }
                }

                InfoAccordion(
                    title = "Counters",
                    items = listOf(
                        InfoItem(
                            "Mileage",
                            list?.d?.cnmKm?.toString()?.plus(" km")?:"0 km"
                        ),
                        InfoItem(
                            "Engine hours",
                            list?.d?.cneh?.toString()?.plus(" h")?:"0 h"
                        ),
                        InfoItem(
                            "GPRS traffic counter",
                            list?.d?.cnkb?.toString()?.plus(" KB")?:"0 KB"
                        )
                    )
                )

                list.d?.prms?.let {parameters->
                    if(parameters.values.isNotEmpty()) {
                        InfoAccordion(
                            title = "Parameters",
                            items = parameters.entries.mapIndexed { index, param ->
                                val paramValue = findParam(param.key, list.d.prms)
                                InfoItem(param.key, if(param.key != "posinfo") paramValue.first?.v?.toString()?:"" else paramValue.second?:"")
                            }
                        )
                    }
                }


                Spacer(Modifier.height(12.dp))
                list.d?.lmsg?.pos?.z?.toString()?.let { InfoItem("Altitude", it+"m") }
                    ?.let { InfoItemUi(item = it) }
                InfoItemUi(item = InfoItem("Satellites", "${list.d?.lmsg?.pos?.sc}"))
                Spacer(Modifier.height(18.dp))

            }



//            UnitSettingsButton(
//                modifier = Modifier.fillMaxWidth(),
//                text = strings.configureTabView,
//                icon = painterResource(Res.drawable.settings_active),
//                onClick = {
//                    navigator.push(InfoTabSettings())
//                }
//            )
        }

        Spacer(Modifier.height(18.dp))
    }
}

data class InfoItem(
    val key: String,
    val value: String
) {
    fun isPhone(): Boolean = value.trim().startsWith("+993")
}

@Composable
fun UnitSettingsButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    text: String,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().background(
            color = MaterialTheme.colorScheme.surface,
        ).clickable {
            onClick()
        }.padding(13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = text,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Icon(
            Icons.AutoMirrored.Filled.ArrowRight,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            contentDescription = "arrow"
        )

    }
}

@Composable
fun InfoAccordion(
    modifier: Modifier = Modifier,
    title: String,
    items: List<InfoItem>
) {
    val open = rememberSaveable {
        mutableStateOf(false)
    }
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable {
                open.value = open.value.not()
            }.background(
                color = MaterialTheme.colorScheme.surface
            ).padding(start = 16.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500
                ),
                color = if (open.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    if (open.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = if (open.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        AnimatedVisibility(open.value) {
            Column(Modifier.fillMaxWidth()) {
                repeat(items.size) { index ->
                    InfoItemUi(item = items[index])
                }
            }
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            thickness = 0.6.dp
        )
    }
}

@Composable
fun InfoItemUi(modifier: Modifier = Modifier, item: InfoItem) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().background(
                color = MaterialTheme.colorScheme.background
            ).padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.key,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W400
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = item.value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500
                ),
                color = MaterialTheme.colorScheme.onBackground,
                textDecoration = if (item.isPhone()) TextDecoration.Underline else TextDecoration.None,
                modifier = Modifier.clickable {

                }
            )
        }
        HorizontalDivider(
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            thickness = 0.6.dp
        )
    }
}
