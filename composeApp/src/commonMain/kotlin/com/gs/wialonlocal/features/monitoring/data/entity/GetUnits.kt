package com.gs.wialonlocal.features.monitoring.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class GetUnits(
    val dataFlags: Int,
    val indexFrom: Int,
    val indexTo: Int,
    val items: List<Item>,
    val searchSpec: SearchSpec,
    val totalItemsCount: Int
)