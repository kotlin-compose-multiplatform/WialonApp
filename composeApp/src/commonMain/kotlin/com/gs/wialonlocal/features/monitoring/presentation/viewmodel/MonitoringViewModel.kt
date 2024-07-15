package com.gs.wialonlocal.features.monitoring.presentation.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.monitoring.domain.usecase.MonitoringUseCase
import com.gs.wialonlocal.features.monitoring.presentation.state.UnitState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MonitoringViewModel(
    private val useCase: MonitoringUseCase
): ScreenModel {
    private val _units = MutableStateFlow(UnitState())
    val units = _units.asStateFlow()

    fun getUnits() {
        screenModelScope.launch {
            useCase.getEvents().onEach {
                when(it) {
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
        if(_units.value.data.isNullOrEmpty()) {
            getUnits()
        }
    }
}