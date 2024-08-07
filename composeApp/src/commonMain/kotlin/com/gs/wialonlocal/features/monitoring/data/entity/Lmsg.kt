package com.gs.wialonlocal.features.monitoring.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Lmsg(
    val f: Int,
    val lc: Int,
    val p: P,
    val pos: Pos,
    val rt: Int,
    val t: Int,
    val tp: String
)