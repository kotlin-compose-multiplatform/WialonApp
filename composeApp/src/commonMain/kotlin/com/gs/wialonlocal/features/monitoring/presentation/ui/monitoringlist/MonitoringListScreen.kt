package com.gs.wialonlocal.features.monitoring.presentation.ui.monitoringlist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.gs.wialonlocal.features.main.presentation.ui.SearchBar
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar
import com.gs.wialonlocal.state.LocalTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.settings_passive

class MonitoringListScreen: Screen {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val pagerState = rememberPagerState { 2 }
        val coroutine = rememberCoroutineScope()
        val theme = LocalTheme.current
        Column(modifier = Modifier.fillMaxSize()) {
            ToolBar {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SearchBar(
                        modifier = Modifier.weight(1f),
                        placeholder = "Search...",
                        onSearch = {query->

                        }
                    )
                    IconButton(
                        onClick = {
                            theme.value = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.settings_passive),
                            contentDescription = "search settings",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                TabRow(
                    pagerState.currentPage,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    indicator = {

                    }
                ) {
                    Tab(pagerState.currentPage == 0, onClick = {
                        coroutine.launch {
                            pagerState.scrollToPage(0)
                        }
                    }, text = {
                        Text("UNITS", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge)
                    })
                    Tab(pagerState.currentPage == 1, onClick = {
                        coroutine.launch {
                            pagerState.scrollToPage(1)
                        }
                    }, text = {
                        Text("GROUPS", color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.bodyLarge)
                    })
                }
            }

            HorizontalPager(pagerState) { index->
                if(index == 0) {
                    Units()
                } else {
                    Groups()
                }
            }
        }
    }
}