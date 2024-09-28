package com.gs.wialonlocal.features.monitoring.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.monitoring.data.entity.history.LoadEventRequest
import com.gs.wialonlocal.features.monitoring.data.entity.history.calculateTripStats
import com.gs.wialonlocal.features.monitoring.domain.usecase.MonitoringUseCase
import com.gs.wialonlocal.features.monitoring.presentation.state.FieldState
import com.gs.wialonlocal.features.monitoring.presentation.state.LoadEventState
import com.gs.wialonlocal.features.monitoring.presentation.state.LocatorState
import com.gs.wialonlocal.features.monitoring.presentation.state.ReportSettingsState
import com.gs.wialonlocal.features.monitoring.presentation.state.SummaryState
import com.gs.wialonlocal.features.monitoring.presentation.state.UnitState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MonitoringViewModel(
    private val useCase: MonitoringUseCase
) : ScreenModel {
    private val _units = MutableStateFlow(UnitState())
    val units = _units.asStateFlow()

    private val _reportSettings = MutableStateFlow(ReportSettingsState())
    val reportSettings = _reportSettings.asStateFlow()

    private val _loadEventState = MutableStateFlow(LoadEventState())
    val loadEventState = _loadEventState.asStateFlow()

    private val _fieldState = MutableStateFlow(FieldState())
    val fieldState = _fieldState.asStateFlow()

    private val _summaryState = MutableStateFlow(SummaryState())
    val summaryState = _summaryState.asStateFlow()

    private val _locatorState = MutableStateFlow(LocatorState())
    val locatorState = _locatorState.asStateFlow()



    fun getLocatorUrl(duration: Long, items: List<String>, onSuccess: (String)-> Unit) {
        screenModelScope.launch {
            useCase.getLocatorUrl(duration, items).onEach { result->
                when(result) {
                    is Resource.Error -> {
                        _locatorState.value = _locatorState.value.copy(
                            loading = false,
                            error = result.message,
                            result = result.data
                        )
                    }
                    is Resource.Loading -> {
                        _locatorState.value = _locatorState.value.copy(
                            loading = true,
                            error = result.message,
                            result = result.data
                        )
                    }
                    is Resource.Success -> {
                        result.data?.let { data->
                            onSuccess("https://gps.ytm.tm/locator/index.html?t=" + data.h)
                        }
                        _locatorState.value = _locatorState.value.copy(
                            loading = false,
                            error = result.message,
                            result = result.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }


    fun getFields(itemId: String) {
        screenModelScope.launch {
            useCase.getEvent(itemId).onEach {
                when (it) {
                    is Resource.Error -> {
                        _fieldState.value = _fieldState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Loading -> {
                        _fieldState.value = _fieldState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Success -> {
                        _fieldState.value = _fieldState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                        getReportSettings(itemId)
                    }
                }

            }.launchIn(this)
        }
    }

    fun loadEvents(itemId: String, timeFrom: Long, timeTo: Long) {
        screenModelScope.launch {
            useCase.loadEvents(
                req = LoadEventRequest(
                    itemId = itemId,
                    timeFrom = timeFrom,
                    timeTo = timeTo
                )
            ).onEach {
                when (it) {
                    is Resource.Error -> {
                        _loadEventState.value = _loadEventState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Loading -> {
                        _loadEventState.value = _loadEventState.value.copy(
                            loading = true,
                            error = it.message,
                            data = it.data
                        )
                    }

                    is Resource.Success -> {
                        println("___________CATEGORIZED_________________________")
                        println(it.data?.second)
                        println("___________CATEGORIZED_________________________")
                        _loadEventState.value = _loadEventState.value.copy(
                            loading = false,
                            error = it.message,
                            data = it.data
                        )
                        it.data?.second?.let { trips ->

                            _summaryState.value = _summaryState.value.copy(
                                tripMin = calculateTripStats(trips.filter {e-> e.type == "trip" }).first,
                                dayKm = calculateTripStats(trips).second,
                                parkingMin = calculateTripStats(trips.filter {e-> e.type == "park" }).first,
                            )
                        }

                        getFields(itemId)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getReportSettings(itemId: String) {
        screenModelScope.launch {
            useCase.getReportSettings(itemId).onEach {
                when (it) {
                    is Resource.Error -> {
                        _reportSettings.value = _reportSettings.value.copy(
                            loading = false,
                            error = it.message,
                            settings = it.data
                        )
                    }

                    is Resource.Loading -> {
                        _reportSettings.value = _reportSettings.value.copy(
                            loading = true,
                            error = it.message,
                            settings = it.data
                        )
                    }

                    is Resource.Success -> {
                        _reportSettings.value = _reportSettings.value.copy(
                            loading = false,
                            error = it.message,
                            settings = it.data
                        )
                    }
                }
            }.launchIn(this)

        }
    }

    fun unloadEvents(id: String, onDone: () -> Unit = {}) {
        screenModelScope.launch {
            useCase.unloadEvents(id).onEach {
                when (it) {
                    is Resource.Error -> {
                        onDone()
                    }

                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        onDone()
                    }
                }
            }.launchIn(this)
        }
    }

    fun startCheckUpdate() {
        screenModelScope.launch {
            while (true) {
                delay(5000L)
                _units.value.data?.let {
                    useCase.getUpdates(it).onEach { result ->
                        when (result) {
                            is Resource.Error -> {}
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                println("Check success")
                                _units.value = _units.value.copy(
                                    data = result.data
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }

        }
    }

    fun getUnits(requireCheckUpdate: Boolean) {
        screenModelScope.launch {
            useCase.getEvents().onEach {
                when (it) {
                    is Resource.Error -> {
                        _units.value = _units.value.copy(
                            loading = false,
                            error = it.message,
                            code = it.code,
                            data = it.data
                        )
                    }

                    is Resource.Loading -> {
                        _units.value = _units.value.copy(
                            loading = true,
                            error = it.message,
                            code = it.code,
                            data = it.data
                        )
                    }

                    is Resource.Success -> {
                        if(requireCheckUpdate) {
                            startCheckUpdate()
                        }
                        _units.value = _units.value.copy(
                            loading = false,
                            error = it.message,
                            code = it.code,
                            data = it.data
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    fun initUnits(requireCheckUpdate: Boolean) {
        if (_units.value.data.isNullOrEmpty()) {
            getUnits(requireCheckUpdate)
        }
    }
}