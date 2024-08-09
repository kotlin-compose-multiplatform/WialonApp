package com.gs.wialonlocal.features.monitoring.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class SearchSpec(
    val itemsType: String,
    val or_logic: String,
    val propName: String,
    val propType: String,
    val propValueMask: String,
    val sortType: String
)