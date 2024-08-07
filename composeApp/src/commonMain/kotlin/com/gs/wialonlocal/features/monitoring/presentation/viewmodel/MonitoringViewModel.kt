package com.gs.wialonlocal.features.monitoring.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.monitoring.domain.usecase.MonitoringUseCase
import com.gs.wialonlocal.features.monitoring.presentation.state.UnitState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MonitoringViewModel(
    private val useCase: MonitoringUseCase
) : ScreenModel {
    private val _units = MutableStateFlow(UnitState())
    val units = _units.asStateFlow()

    fun startCheckUpdate() {
        screenModelScope.launch {
            _units.value.data?.let {
                while (true) {
                    delay(5000L)
                    useCase.getUpdates(it).onEach { result->
                        when(result) {
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

    fun getUnits() {
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
                        startCheckUpdate()
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

    fun initUnits() {
        if (_units.value.data.isNullOrEmpty()) {
            getUnits()
        }
    }
}