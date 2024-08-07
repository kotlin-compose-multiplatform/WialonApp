package com.gs.wialonlocal.features.monitoring.domain.model

data class UnitModel(
    val id: String,
    val carNumber: String,
    val number: String,
    val image: String,
    val lastOnlineTime: String,
    val address: String,
    val speed: String,
    val estimateTime: String,
    val estimateDistance: String,
    val isOnline: Boolean,
    val latitude: Double,
    val longitude: Double,
)
