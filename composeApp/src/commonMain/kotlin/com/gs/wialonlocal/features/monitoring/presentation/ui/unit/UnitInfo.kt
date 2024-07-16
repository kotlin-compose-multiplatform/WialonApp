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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import wialonlocal.composeapp.generated.resources.Res
import wialonlocal.composeapp.generated.resources.settings_active

@Composable
fun UnitInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(6) {
            InfoAccordion(
                title = "Custom fields",
                items = listOf(
                    InfoItem("test", "test"),
                    InfoItem("Telefon", "+993 62 73 72 22"),
                    InfoItem("test", "test"),
                    InfoItem("test", "test"),
                    InfoItem("test", "test"),
                    InfoItem("test", "test"),
                    InfoItem("test", "test"),
                    InfoItem("test", "test"),
                )
            )
        }

        Spacer(Modifier.height(12.dp))
        InfoItemUi(item = InfoItem("test", "test"))
        InfoItemUi(item = InfoItem("test", "test"))
        Spacer(Modifier.height(18.dp))
        UnitSettingsButton(
            modifier = Modifier.fillMaxWidth(),
            text = strings.configureTabView,
            icon = painterResource(Res.drawable.settings_active),
            onClick = {

            }
        )
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
