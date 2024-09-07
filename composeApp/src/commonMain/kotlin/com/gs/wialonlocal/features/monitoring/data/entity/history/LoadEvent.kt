package com.gs.wialonlocal.features.monitoring.data.entity.history

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement

@Serializable
data class TripEvent(
    val trips: Map<String, Int>
)

@Serializable
data class TripState(
    val trips: TripStateData
)

@Serializable
data class TripStateData(
    val updateTime: Long
)

@Serializable
data class TripSelector(
    val trips: Map<String, List<Trip>>
)

@Serializable
data class Trip(
    val from: TripPoint,
    val to: TripPoint,
    val m: Long,
    val f: Int,
    val state: Int,
    val max_speed: Int,
    val curr_speed: Int,
    val avg_speed: Int,
    val distance: Int,
    val odometer: Long,
    val course: Int,
    val altitude: Int,
    val pos_flags: Int,
    val format: TripFormat,
    val track: String,
    val p: JsonObject
)

@Serializable
data class TripPoint(
    val t: Long,
    val y: Double,
    val x: Double
)

@Serializable
data class TripFormat(
    val distance: String,
    val avg_speed: String
)

@Serializable
data class TripsResponse(
    val events: TripEvent,
    val states: TripState,
    val selector: TripSelector
)
