package com.gs.wialonlocal.features.monitoring.data.entity

import com.gs.wialonlocal.core.constant.Constant
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val cls: Int,
    val id: Int,
    val mu: Int,
    val nm: String,
    val uacl: Long,
    val ugi: Int,
    val uri: String
) {
    fun toUiEntity(): UnitModel {
        return UnitModel(
            id = id.toString(),
            carNumber = nm,
            number = "87",
            image = "${Constant.BASE_URL}${uri}",
            lastOnlineTime = "0",
            address = "",
            speed = "0km/h",
            estimateTime = "0",
            estimateDistance = "0",
            isOnline = false,
            latitude = 0.0,
            longitude = 0.0,
        )
    }
}