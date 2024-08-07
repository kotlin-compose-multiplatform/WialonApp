package com.gs.wialonlocal.features.monitoring.data.repository

import com.gs.wialonlocal.core.constant.Constant
import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.auth.data.AuthSettings
import com.gs.wialonlocal.features.monitoring.data.entity.GetUnits
import com.gs.wialonlocal.features.monitoring.data.entity.updates.Data
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import com.gs.wialonlocal.features.monitoring.domain.repository.MonitoringRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Parameters
import io.ktor.http.parameters
import io.ktor.util.InternalAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

private val json = Json {
    ignoreUnknownKeys = true
}

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
                    append("params", "{\"spec\":{\"itemsType\":\"avl_unit\",\"sortType\":\"sys_name\",\"propName\":\"sys_name\",\"propValueMask\":\"*\"}, \"force\":1, \"flags\":1041, \"from\":0, \"to\":0}")
                })
            }.body<GetUnits>()
            addUnitsToUpdate(result.items.map { it.id.toLong() })

            val locations = result.items.map { it.getLocation() }.joinToString(",")

            val address = httpClient.get("${Constant.BASE_URL}/gis_geocode?coords=[${locations}]&flags=1255211008&uid=${authSettings.getId()}").body<List<String>>()

            emit(Resource.Success(
                data = result.items.mapIndexed { index, item ->
                    item.toUiEntity(address[index])
                }
            ))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message, 500))
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun addUnitsToUpdate(ids: List<Long>) {
        try {
            val units = ids.joinToString(",") { "{\"id\":${it},\"detect\":{\"*\":0}}" }
            val result = httpClient.post("https://gps.ytm.tm/wialon/ajax.html?svc=events/update_units") {
                body = FormDataContent(Parameters.build {
                    append("sid", authSettings.getSessionId())
                    append("params", "{\"mode\":\"add\", \"units\":[${units}]}")
                })
            }.bodyAsText()
            println("Add units: $result")

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    @OptIn(InternalAPI::class)
    override suspend fun getUpdates(oldEvents: List<UnitModel>): Flow<Resource<List<UnitModel>>> = flow {
        emit(Resource.Loading())
        try {
            val jsonResponse = httpClient.post("${Constant.BASE_URL}/wialon/ajax.html?svc=events/check_updates") {
                body = FormDataContent(Parameters.build {
                    append("sid", authSettings.getSessionId())
                    append("params", "{\"lang\":\"tk\",\"measure\":0,\"detalization\":35}")
                })
            }.body<JsonObject>()
//            println("___________________________________________________________")
//            println("UPDATE: "+jsonResponse.entries)
//            println("___________________________________________________________")
            if (jsonResponse.isEmpty()) {
                println("Response is empty")
                emit(Resource.Error("Response is empty"))
            } else {
//                println("UPDATE: ${jsonResponse.keys}")
//                println("UPDATE: ${jsonResponse.values}")
                jsonResponse.entries.forEach { entry ->
                    val dynamicKey = entry.key
//                    println("UPDATE: $dynamicKey")
                    val dataArray = entry.value.jsonArray
                    val data = dataArray[0].jsonObject
//                    println("UPDATE: $data")
                    val decode = json.decodeFromString<Data>(data.toString())
                    println("UPDATE: $decode")
//                    dataArray.forEach { dataElement ->
//                        println("UPDATE data elemen: $dataElement")
//                        val dataObject = dataElement.jsonObject
//                        val data = Json.decodeFromJsonElement<Data>(dataObject)
//
//                        // Now you can work with the `data` object
//                        println("Data for key $dynamicKey: $data")
//                    }
                }
               // val address = httpClient.get("${Constant.BASE_URL}/gis_geocode?coords=[${locations}]&flags=1255211008&uid=${authSettings.getId()}").body<List<String>>()

            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.message))
        }
    }

}