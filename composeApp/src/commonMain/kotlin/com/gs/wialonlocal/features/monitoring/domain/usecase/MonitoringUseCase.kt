package com.gs.wialonlocal.features.monitoring.domain.usecase

import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import com.gs.wialonlocal.features.monitoring.domain.repository.MonitoringRepository
import kotlinx.coroutines.flow.Flow

class MonitoringUseCase(private val repository: MonitoringRepository) {
    suspend fun getEvents(): Flow<Resource<List<UnitModel>>> {
        return repository.getEvents()
    }
    suspend fun getUpdates(oldEvents: List<UnitModel>): Flow<Resource<List<UnitModel>>> {
        return repository.getUpdates(oldEvents)
    }
}