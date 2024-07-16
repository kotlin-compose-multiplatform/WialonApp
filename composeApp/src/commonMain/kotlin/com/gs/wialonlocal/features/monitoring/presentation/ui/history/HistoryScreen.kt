package com.gs.wialonlocal.features.monitoring.presentation.ui.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.jetbrains.compose.resources.painterResource
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.circle_car
import wialonlocal.composeapp.generated.resources.circle_graph
import wialonlocal.composeapp.generated.resources.circle_parking
import wialonlocal.composeapp.generated.resources.clock
import wialonlocal.composeapp.generated.resources.key
import wialonlocal.composeapp.generated.resources.line_graph
import wialonlocal.composeapp.generated.resources.max_speed

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
) {
    val selectedDate = rememberSaveable {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()).background(
            color = MaterialTheme.colorScheme.background
        )
    ) {
        Spacer(Modifier.height(4.dp))
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(
                horizontal = 16.dp
            )
        ) {
            items(20) {
                HistoryDateButton(
                    dayOfWeek = "Sat",
                    day = "${it.plus(1)}",
                    month = "Jul",
                    selected = selectedDate.value == it,
                    onClick = {
                        selectedDate.value = it
                    }
                )
            }
        }
        Spacer(Modifier.height(4.dp))

        HorizontalDivider()

        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HistoryStatLabel(
                modifier = Modifier.weight(1f),
                icon = painterResource(Res.drawable.circle_car),
                text = "36 min"
            )
            HistoryStatLabel(
                modifier = Modifier.weight(1f),
                icon = painterResource(Res.drawable.circle_graph),
                text = "3.22 km"
            )
            HistoryStatLabel(
                modifier = Modifier.weight(1f),
                icon = painterResource(Res.drawable.circle_parking),
                text = "9h 15min"
            )
        }
        HorizontalDivider()
        repeat(20) { index->
            if(index % 2 == 0) {
                ParkingItem(Modifier.fillMaxWidth())
            } else {
                CarItem(Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun ParkingItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(72.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "8:51", style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "AM", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.circle_parking),
                contentDescription = "parking",
                modifier = Modifier.size(30.dp).absoluteOffset(x = (-27).dp, y = 12.dp).zIndex(3f).background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
            )

            Column(modifier = Modifier.fillMaxWidth().padding(start = 10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.height(30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.clock),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "5 min",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.height(30.dp).padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.key),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "5 min",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500
                            )
                        )
                    }
                }
                Text(
                    text = "Ankara (1956) Köçesi, Aşgabat, Türkmenistan",
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun CarItem(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondary.copy(
                    alpha = 0.1f
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.width(72.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "8:51", style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "AM", style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.inverseSurface
                )
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.circle_car),
                contentDescription = "car",
                modifier = Modifier.size(30.dp).absoluteOffset(x = (-27).dp).zIndex(3f)
            )

            Column(modifier = Modifier.fillMaxWidth().padding(start = 10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.height(30.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.clock),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "5 min",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.height(30.dp).padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.line_graph),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "1.67 km",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500
                            )
                        )
                    }

                    Row(
                        modifier = Modifier.height(30.dp).padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.max_speed),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "47 km/h",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W500
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HistoryStatLabel(
    modifier: Modifier = Modifier,
    icon: Painter,
    text: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = text,
            modifier = Modifier.size(30.dp)
        )
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun HistoryDateButton(
    modifier: Modifier = Modifier,
    dayOfWeek: String,
    day: String,
    month: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Column(
        modifier = modifier
            .clip(shape)
            .background(
                color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = shape
            )
            .border(
                width = if (selected) 0.dp else 1.dp,
                shape = shape,
                color = MaterialTheme.colorScheme.inverseSurface
            )
            .clickable {
                onClick()
            }
            .padding(horizontal = 18.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            dayOfWeek,
            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                day,
                color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500
                )
            )
            Text(
                month,
                color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}