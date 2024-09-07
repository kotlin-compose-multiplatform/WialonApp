package com.gs.wialonlocal.features.monitoring.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Lmsg(
    val f: Int,
    val lc: Long,
    val p: P? = null,
    val pos: Pos? = null,
    val rt: Int,
    val t: Int,
    val tp: String
)

//fun Long.format(): String {
//    val instant = Instant.fromEpochMilliseconds(timestamp)
//    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
//    return localDateTime.toString()
//}