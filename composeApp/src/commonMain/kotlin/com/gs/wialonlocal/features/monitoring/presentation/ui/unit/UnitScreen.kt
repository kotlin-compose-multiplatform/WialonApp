package com.gs.wialonlocal.features.monitoring.presentation.ui.unit

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.internal.BackHandler
import com.gs.wialonlocal.common.GoogleMaps
import com.gs.wialonlocal.common.LatLong
import com.gs.wialonlocal.common.LatLongZoom
import com.gs.wialonlocal.components.ContextButton
import com.gs.wialonlocal.components.ContextMenu
import com.gs.wialonlocal.components.ImageLoader
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar
import com.gs.wialonlocal.features.map.presentation.ui.MapContainer
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import com.gs.wialonlocal.features.monitoring.presentation.ui.history.HistoryScreen
import com.gs.wialonlocal.features.monitoring.presentation.ui.history.getStartAndEndOfDayTimestamps
import com.gs.wialonlocal.features.monitoring.presentation.ui.monitoringlist.Groups
import com.gs.wialonlocal.features.monitoring.presentation.ui.monitoringlist.Units
import com.gs.wialonlocal.features.monitoring.presentation.viewmodel.MonitoringViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.clock
import wialonlocal.composeapp.generated.resources.copy_coordinate
import wialonlocal.composeapp.generated.resources.distance
import wialonlocal.composeapp.generated.resources.edit
import wialonlocal.composeapp.generated.resources.execute_report
import wialonlocal.composeapp.generated.resources.forward
import wialonlocal.composeapp.generated.resources.key
import wialonlocal.composeapp.generated.resources.key_off
import wialonlocal.composeapp.generated.resources.navigation_apps
import wialonlocal.composeapp.generated.resources.send_command
import wialonlocal.composeapp.generated.resources.share_location

class UnitScreen(
    private val id: String,
    private val model: UnitModel
) : Screen {
    @Composable
    override fun Content() {
        UnitDetails(id = id, unitModel = model)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class, InternalVoyagerApi::class)
@Composable
fun UnitDetails(modifier: Modifier = Modifier, id: String, unitModel: UnitModel) {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: MonitoringViewModel = navigator.koinNavigatorScreenModel()
    val units = viewModel.units.collectAsState()
    val fields = viewModel.fieldState.collectAsState()

    val startDate = rememberSaveable {
        mutableStateOf(
            getStartAndEndOfDayTimestamps(
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            ).first
        )
    }

    val endDate = rememberSaveable {
        mutableStateOf(
            getStartAndEndOfDayTimestamps(
                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            ).second
        )
    }

    BackHandler(true) {
        viewModel.unloadEvents(id)
        navigator.pop()
    }

    LaunchedEffect(id, startDate.value, endDate.value) {
        viewModel.unloadEvents(id) {
            viewModel.loadEvents(id, startDate.value, endDate.value)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        val pagerState = rememberPagerState { 2 }
        val coroutine = rememberCoroutineScope()
        ToolBar {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(
                    onClick = {
                        viewModel.unloadEvents(id)
                        navigator.pop()
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        text = unitModel.carNumber,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                IconButton(
                    onClick = {
                    }
                ) {
                    Icon(
                        Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }


            }

            TabRow(
                pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Tab(pagerState.currentPage == 0, onClick = {
                    coroutine.launch {
                        pagerState.scrollToPage(0)
                    }
                }, text = {
                    androidx.compose.material.Text(
                        "INFO",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                })
                Tab(pagerState.currentPage == 1, onClick = {
                    coroutine.launch {
                        pagerState.scrollToPage(1)
                    }
                }, text = {
                    androidx.compose.material.Text(
                        "HISTORY",
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = MaterialTheme.typography.bodyLarge
                    )
                })
            }
        }

        BottomSheetScaffold(
            containerColor = MaterialTheme.colorScheme.surface,
            sheetTonalElevation = 0.dp,
            sheetDragHandle = {},
            sheetShape = RoundedCornerShape(0.dp),
            sheetPeekHeight = 300.dp,
            sheetContent = {
                Column(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        UnitItem(
                            Modifier.weight(1f),
                            item = units.value.data!!.find { it.id == id }!!
                        )
                        Column(
                            Modifier.height(100.dp),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            IconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowUp,
                                    contentDescription = "up",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            IconButton(
                                onClick = {

                                }
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowDown,
                                    contentDescription = "down",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                    HorizontalPager(pagerState) { index ->
                        if (index == 0) {
                            UnitInfo(Modifier.fillMaxSize())
                        } else {
                            HistoryScreen(
                                Modifier.fillMaxSize(),
                                onDateChanges = { start, end ->
                                    startDate.value = start
                                    endDate.value = end
                                }
                            )
                        }
                    }
                }
            }
        ) {
            MapContainer(Modifier.fillMaxSize()) {
                GoogleMaps(
                    modifier = Modifier.fillMaxSize(),
                    shouldZoomToLatLongZoom = LatLongZoom(LatLong(37.8, 58.7), 18f),
                    onDidAllowCacheReset = {

                    },
                    onDidCenterCameraOnLatLong = {

                    },
                    onDidZoomToLatLongZoom = {

                    },
                    shouldAllowCacheReset = true
                )
            }
        }
    }
}

@Composable
internal fun UnitItem(
    modifier: Modifier = Modifier,
    item: UnitModel
) {
    Column(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
            ) {
                Row(Modifier.padding(start = 16.dp)) {
                    ImageLoader(
                        modifier = Modifier.size(40.dp),
                        url = item.image,
                        contentScale = ContentScale.FillBounds
                    )
                }

                val shape = RoundedCornerShape(
                    topEnd = 4.dp,
                    bottomEnd = 4.dp
                )
                Spacer(Modifier.height(12.dp))
                Box(
                    modifier = Modifier.defaultMinSize(minWidth = 80.dp).clip(shape).background(
                        color = item.calculateDifference().second,
                        shape = shape
                    ).padding(
                        horizontal = 24.dp,
                        vertical = 4.dp
                    )
                ) {
                    androidx.compose.material.Text(
                        text = item.calculateDifference().first,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.W400
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }


            }
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                androidx.compose.material.Text(
                    text = item.carNumber,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W500
                    )
                )
                Spacer(Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    if (item.isOnline) {
                        Row(
                            modifier = Modifier.height(30.dp).background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(6.dp)
                            ).padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material.Icon(
                                painter = painterResource(Res.drawable.forward),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                            Spacer(Modifier.width(6.dp))
                            androidx.compose.material.Text(
                                text = item.speed,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.W500
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.height(30.dp).background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(
                                alpha = 0.1f
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ).padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material.Icon(
                            painter = painterResource(if (item.isOnline) Res.drawable.key else Res.drawable.key_off),
                            contentDescription = null,
                            tint = if (item.isOnline) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        modifier = Modifier.height(30.dp).background(
                            color = MaterialTheme.colorScheme.primaryContainer.copy(
                                alpha = 0.1f
                            ),
                            shape = RoundedCornerShape(6.dp)
                        ).padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        androidx.compose.material.Icon(
                            painter = painterResource(Res.drawable.clock),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(6.dp))
                        androidx.compose.material.Text(
                            text = item.convertTime(),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500
                            )
                        )
                    }

                    if (item.isOnline) {
                        Row(
                            modifier = Modifier.height(30.dp).background(
                                color = MaterialTheme.colorScheme.primaryContainer.copy(
                                    alpha = 0.1f
                                ),
                                shape = RoundedCornerShape(6.dp)
                            ).padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            androidx.compose.material.Icon(
                                painter = painterResource(Res.drawable.distance),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(Modifier.width(6.dp))
                            androidx.compose.material.Text(
                                text = item.estimateDistance,
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.W500
                                )
                            )
                        }
                    }
                }
                Spacer(Modifier.height(6.dp))
                androidx.compose.material.Text(
                    text = item.address,
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodySmall
                )
            }

        }
    }
}