package com.gs.wialonlocal.features.status.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.features.geofence.presentation.ui.GeoFenceScreen
import com.gs.wialonlocal.features.geofence.presentation.ui.Geofences
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.ellipse
import wialonlocal.composeapp.generated.resources.ignation_on
import wialonlocal.composeapp.generated.resources.no_message
import wialonlocal.composeapp.generated.resources.no_state
import wialonlocal.composeapp.generated.resources.offline
import wialonlocal.composeapp.generated.resources.online
import wialonlocal.composeapp.generated.resources.stationary

class StatusScreen : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            ToolBar(Modifier) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = strings.status,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W500,
                        fontSize = 20.sp
                    )
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.online,
                        titleColor = Color(0xFF4DB251),
                        value = "6",
                        icon = painterResource(Res.drawable.online),
                        iconColor = Color(0xFF4DB251).copy(alpha = 0.4f)
                    )
                }

                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.offline,
                        titleColor = Color(0xFF6C788E),
                        value = "10",
                        icon = painterResource(Res.drawable.offline),
                        iconColor = Color(0xFF6C788E).copy(alpha = 0.5f)
                    )
                }

                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.stationary,
                        titleColor = Color(0xFFF44639),
                        value = "6",
                        icon = painterResource(Res.drawable.stationary),
                        iconColor = Color(0xFFF44639).copy(alpha = 0.5f)
                    )
                }

                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.ignitionOn,
                        titleColor = Color(0xFFF44639),
                        value = "1",
                        icon = painterResource(Res.drawable.ignation_on),
                        iconColor = Color(0xFFF44639).copy(alpha = 0.5f)
                    )
                }

                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.moving,
                        titleColor = Color(0xFF4DB251),
                        value = "7",
                        icon = painterResource(Res.drawable.no_state),
                        iconColor = Color(0xFF4DB251).copy(alpha = 0.5f)
                    )
                }

                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.lbsDetected,
                        titleColor = Color(0xFFFF9A05),
                        value = "0",
                        icon = painterResource(Res.drawable.online),
                        iconColor = Color(0xFFFF9A05).copy(alpha = 0.5f)
                    )
                }

                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.noActualState,
                        titleColor = Color(0xFF6C788E),
                        value = "2",
                        icon = painterResource(Res.drawable.no_state),
                        iconColor = Color(0xFF6C788E).copy(alpha = 0.5f)
                    )
                }

                item {
                    StatusButton(
                        modifier = Modifier.fillMaxWidth(),
                        title = strings.noMessages,
                        titleColor = Color(0xFF6C788E),
                        value = "0",
                        icon = painterResource(Res.drawable.no_message),
                        iconColor = Color(0xFF6C788E).copy(alpha = 0.5f)
                    )
                }
            }

            Spacer(Modifier.height(12.dp))
            Row(
                Modifier.fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surface
                ).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = strings.geofences,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = strings.showAll,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.clickable {
                        navigator.push(GeoFenceScreen(isBackEnabled = true))
                    }
                )
            }
            Spacer(Modifier.height(12.dp))


        }
    }
}

@Composable
fun StatusButton(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    value: String,
    icon: Painter,
    iconColor: Color = MaterialTheme.colorScheme.primary
) {
    val navigator = LocalNavigator.currentOrThrow
    Column(
        modifier = modifier.clickable {
            navigator.push(StatusViewScreen())
        }.background(
            color = MaterialTheme.colorScheme.surface
        )
    ) {
        Text(
            text = title,
            color = titleColor,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(12.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 20.sp
                ),
                modifier = Modifier.weight(1f).padding(12.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Icon(
                painter = icon,
                tint = iconColor,
                contentDescription = title,
                modifier = Modifier.size(56.dp)
            )
        }
    }
}