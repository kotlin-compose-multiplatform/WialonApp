package com.gs.wialonlocal.features.monitoring.presentation.ui.unit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar

class SelectUnits: Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        Column(Modifier.fillMaxSize()) {
            LargeTopAppBar(
                title = {
                    Text("Title", color = MaterialTheme.colorScheme.onPrimary)
                },
                navigationIcon = {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "close",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                },
                actions = {
                    Text("actions", color = MaterialTheme.colorScheme.onPrimary)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )

            LazyColumn {
                items(100) {
                    Text("items", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}