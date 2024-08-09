package com.gs.wialonlocal.features.status.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.components.BackFragment
import com.gs.wialonlocal.components.CheckText
import com.gs.wialonlocal.components.ContextButton
import com.gs.wialonlocal.components.ContextMenu
import com.gs.wialonlocal.components.ImageLoader
import com.gs.wialonlocal.components.RadioText
import com.gs.wialonlocal.components.SwitchText
import com.gs.wialonlocal.features.main.presentation.ui.SearchBar
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.copy_coordinate
import wialonlocal.composeapp.generated.resources.edit
import wialonlocal.composeapp.generated.resources.execute_report
import wialonlocal.composeapp.generated.resources.navigation_apps
import wialonlocal.composeapp.generated.resources.send_command
import wialonlocal.composeapp.generated.resources.share_location

class StatusViewScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        BackFragment(
            modifier = Modifier.fillMaxSize(),
            title = strings.unit,
            onBackPresses = {
                navigator.pop()
            },
            badge = {
                Box(Modifier.background(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.5f
                    ),
                    shape = CircleShape
                ).padding(2.dp).width(17.dp).height(30.dp), contentAlignment = Alignment.Center) {
                    Text(
                        "10",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            toolbar = {
                Box(Modifier.padding(16.dp).fillMaxWidth()) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = strings.search,
                        onSearch = {

                        }
                    )
                }
            }
        ) {
            Column(
                Modifier.fillMaxSize().background(
                    MaterialTheme.colorScheme.background
                ).verticalScroll(rememberScrollState())
            ) {
                repeat(30) {
                    StatusCar(
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun StatusCar(
    modifier: Modifier = Modifier
) {
    val open = rememberSaveable {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.surface
        ).padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageLoader(
            modifier = Modifier.size(40.dp).clip(CircleShape).border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = CircleShape
            ),
            url = "",
            contentScale = ContentScale.FillBounds
        )

        Spacer(Modifier.width(12.dp))

        Text(
            "5280AGH 87",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )


        IconButton(
            onClick = {
                open.value = true
            }
        ) {
            ContextMenu(
                buttons = listOf(
                    ContextButton(
                        text = strings.sendCommands,
                        icon = painterResource(Res.drawable.send_command),
                        enabled = true,
                        onClick = {}
                    ),
                    ContextButton(
                        text = strings.shareLocation,
                        icon = painterResource(Res.drawable.share_location),
                        enabled = true,
                        onClick = {}
                    ),
                    ContextButton(
                        text = strings.navigationApps,
                        icon = painterResource(Res.drawable.navigation_apps),
                        enabled = true,
                        onClick = {}
                    ),
                    ContextButton(
                        text = strings.copyCoordinates,
                        icon = painterResource(Res.drawable.copy_coordinate),
                        enabled = true,
                        onClick = {}
                    ),
                    ContextButton(
                        text = strings.executeReports,
                        icon = painterResource(Res.drawable.execute_report),
                        enabled = true,
                        onClick = {}
                    ),
                    ContextButton(
                        text = strings.edit,
                        icon = painterResource(Res.drawable.edit),
                        enabled = true,
                        onClick = {}
                    )
                ),
                open = open.value,
                onDismiss = {
                    open.value = false
                }
            )
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "more",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


    }
}