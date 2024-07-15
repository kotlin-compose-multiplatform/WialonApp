package com.gs.wialonlocal.features.monitoring.data.repository

import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.core.settings.AuthSettings
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import com.gs.wialonlocal.features.monitoring.domain.repository.MonitoringRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MonitoringRepositoryImpl(
    private val authSettings: AuthSettings
) : MonitoringRepository {
    override suspend fun getEvents(): Flow<Resource<List<UnitModel>>> = flow {
        emit(Resource.Loading())
        try {
            delay(3000L)
            emit(Resource.Success(
                data = List(40) {
                    val isOnline = it%5==0
                    UnitModel(
                        id = "1",
                        carNumber = "1293AGE",
                        number = "87",
                        image = "",
                        lastOnlineTime = if(isOnline) "159 d" else "3 s",
                        address = "Parahat 4, Aşgabat, Turkmenistan",
                        speed = "24 km/h",
                        estimateTime = if(isOnline) "159 d 19 h" else "1 min",
                        estimateDistance = "0.77 km",
                        isOnline = isOnline.not(),
                        latitude = 37.2345,
                        longitude = 58.4545,
                    )
                }
            ))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message, 500))
        }
    }

}