package com.gs.wialonlocal.features.monitoring.presentation.ui.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.lyricist.strings
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gs.wialonlocal.components.ImageLoader
import com.gs.wialonlocal.components.SwitchText
import com.gs.wialonlocal.features.main.presentation.ui.SearchBar
import com.gs.wialonlocal.features.main.presentation.ui.ToolBar
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import com.gs.wialonlocal.features.monitoring.presentation.ui.settings.WorkListSettings
import com.gs.wialonlocal.features.monitoring.presentation.viewmodel.MonitoringViewModel

class SelectUnits : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: MonitoringViewModel = navigator.koinNavigatorScreenModel()
        val units = viewModel.units.collectAsState()
        Column(
            Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(
                color = MaterialTheme.colorScheme.background
            )
        ) {
            LargeTopAppBar(
                title = {
                    Row(
                        Modifier.fillMaxWidth().padding(end = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        SearchBar(
                            modifier = Modifier.weight(1f),
                            placeholder = strings.search,
                            onSearch = {

                            }
                        )
//                        IconButton(
//                            onClick = {}
//                        ) {
//                            Icon(
//                                Icons.Filled.CheckBox,
//                                contentDescription = "check",
//                                tint = MaterialTheme.colorScheme.onPrimary
//                            )
//                        }
                    }
                },
                navigationIcon = {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "close",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.clickable {
                                navigator.pop()
                            }
                        )
                        Text(
                            strings.selectItems,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.W500,
                                fontSize = 20.sp
                            )
                        )
                    }
                },
                actions = {
//                    Icon(
//                        Icons.Default.Check,
//                        contentDescription = "done",
//                        tint = MaterialTheme.colorScheme.onPrimary,
//                        modifier = Modifier.padding(12.dp),
//                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(Modifier.height(12.dp))
            SwitchText(
                modifier = Modifier.fillMaxWidth().clickable {
                    navigator.push(WorkListSettings())
                },
                arrow = true,
                text = strings.workList
            )
            Spacer(Modifier.height(12.dp))
            units.value.data?.let { list->
                repeat(list.count()) { index->
                    val item = list[index]
                    SelectCar(
                        modifier = Modifier.fillMaxWidth(),
                        unitModel = item
                    )
                    HorizontalDivider(
                        thickness = 0.7.dp
                    )
                }
            }


        }
    }
}


@Composable
fun SelectCar(
    modifier: Modifier = Modifier,
    unitModel: UnitModel
) {
    val check = remember {
        mutableStateOf(true)
    }
    Row(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.surface
        ).clickable {
            check.value = check.value.not()
        }.padding(vertical = 6.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageLoader(
            modifier = Modifier.size(40.dp).clip(CircleShape).border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.inverseSurface,
                shape = CircleShape
            ),
            url = unitModel.image,
            contentScale = ContentScale.FillBounds
        )

        Spacer(Modifier.width(12.dp))

        Text(
            unitModel.carNumber,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Checkbox(
            checked = check.value,
            onCheckedChange = {
                check.value = it
            }
        )
    }
}