package com.gs.wialonlocal.features.monitoring.domain.repository

import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import kotlinx.coroutines.flow.Flow

interface MonitoringRepository {
    suspend fun getEvents(): Flow<Resource<List<UnitModel>>>
    suspend fun getUpdates(oldEvents: List<UnitModel>): Flow<Resource<List<UnitModel>>>
}