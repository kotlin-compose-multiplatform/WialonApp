package com.gs.wialonlocal.features.map.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.screen.Screen
import com.gs.wialonlocal.common.GoogleMaps
import com.gs.wialonlocal.common.LatLong
import com.gs.wialonlocal.common.LatLongZoom
import com.gs.wialonlocal.features.main.presentation.ui.SearchBar
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar

class MapScreen: Screen {
    @Composable
    override fun Content() {
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
            MapContainer {
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