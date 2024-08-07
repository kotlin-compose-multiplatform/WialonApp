package com.gs.wialonlocal.features.monitoring.data.repository

import com.gs.wialonlocal.core.constant.Constant
import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.auth.data.AuthSettings
import com.gs.wialonlocal.features.monitoring.data.entity.GetUnits
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import com.gs.wialonlocal.features.monitoring.domain.repository.MonitoringRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.Parameters
import io.ktor.http.append
import io.ktor.http.parameters
import io.ktor.util.InternalAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MonitoringRepositoryImpl(
    private val authSettings: AuthSettings,
    private val httpClient: HttpClient
) : MonitoringRepository {
    @OptIn(InternalAPI::class)
    override suspend fun getEvents(): Flow<Resource<List<UnitModel>>> = flow {
        emit(Resource.Loading())
        try {
            val result = httpClient.post("${Constant.BASE_URL}/wialon/ajax.html?svc=core/search_items") {
                body = FormDataContent(Parameters.build {
                    append("sid", authSettings.getSessionId())
                    append("params", "{\"flags\":17,\"force\":1,\"from\":0,\"to\":0,\"spec\":{\"itemsType\":\"avl_unit\",\"sortType\":\"sys_name\",\"propName\":\"sys_name\",\"propValueMask\":\"*\"}}")
                })
            }.body<GetUnits>()
            emit(Resource.Success(
                data = result.items.map {
                    it.toUiEntity()
                }
            ))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message, 500))
        }
    }

    override suspend fun getUpdates(oldEvents: List<UnitModel>): Flow<Resource<List<UnitModel>>> = flow {
        emit(Resource.Loading())
        try {
//            val result =
            val addressResult = httpClient.get("${Constant.BASE_URL}/gis_geocode") {
                parameters {
                    append("coords", "[{%22lat%22:37.9735983,%22lon%22:58.33806}]")
                    append("flags", "1255211008")
                    append("city_radius", "10.0")
                    append("dist_from_unit", "5.0")
                    append("txt_dist", "")
                    append("house_detect_radius", "0")
                    append("uid", "12732")
                    append("gis_sid", authSettings.getGisSid())
                    append("sid", authSettings.getSessionId())
                }
            }
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message))
        }
    }

}