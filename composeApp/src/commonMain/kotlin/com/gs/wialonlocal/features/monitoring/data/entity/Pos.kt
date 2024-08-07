package com.gs.wialonlocal.features.monitoring.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Pos(
    val c: Int,
    val s: Int,
    val sc: Int,
    val x: Double,
    val y: Double,
    val z: Int
)