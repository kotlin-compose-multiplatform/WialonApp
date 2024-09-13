package com.gs.wialonlocal.features.map.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.common.GoogleMaps
import com.gs.wialonlocal.common.LatLong
import com.gs.wialonlocal.common.LatLongZoom
import com.gs.wialonlocal.features.main.presentation.ui.SearchBar
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar
import com.gs.wialonlocal.features.monitoring.presentation.ui.unit.UnitScreen
import com.gs.wialonlocal.features.monitoring.presentation.viewmodel.MonitoringViewModel

class MapScreen: Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = navigator.koinNavigatorScreenModel<MonitoringViewModel>()
        val units = viewModel.units.collectAsState()
        LaunchedEffect(true) {
            viewModel.initUnits()
        }
        Column(Modifier.fillMaxSize()) {
            ToolBar {
                Box(Modifier.padding(16.dp)) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = strings.search,
                        onSearch = {

                        }
                    )
                }
            }
            units.value.data?.let { units->
                MapContainer {
                    GoogleMaps(
                        modifier = Modifier.fillMaxSize(),
                        units = units,
                        onUnitClick = { unit->
                            navigator.push(UnitScreen(unit.id, unit))
                        }
                    )
                }
            }

        }
    }
}