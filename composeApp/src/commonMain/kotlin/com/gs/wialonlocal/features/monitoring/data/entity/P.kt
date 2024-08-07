package com.gs.wialonlocal.features.monitoring.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class P(
    val event_io_id: Int,
    val io_1: Int,
    val io_16: Int,
    val io_239: Int,
    val io_24: Int,
    val io_240: Int,
    val io_66: Int,
    val io_67: Int,
    val io_68: Int,
    val prior: Int,
    val pwr_ext: Int,
    val pwr_int: Double,
    val total_io: Int
)