package com.gs.wialonlocal.features.monitoring.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class UnitRequest(
    val from: Int = 0,
    val to: Int = 0
)
