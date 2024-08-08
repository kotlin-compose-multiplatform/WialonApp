package com.gs.wialonlocal.features.monitoring.data.entity

import com.gs.wialonlocal.core.constant.Constant
import com.gs.wialonlocal.features.monitoring.domain.model.UnitModel
import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val cls: Int,
    val id: Int,
    val lmsg: Lmsg? = null,
    val mu: Int,
    val nm: String,
    val pos: PosX? = null,
    val uacl: Long,
    val ugi: Int,
    val uri: String
) {
    fun getLocation(): String {
        return "{\"lon\":${pos?.x?:0},\"lat\":${pos?.y?:0}}"
    }

    fun toUiEntity(address: String): UnitModel {
        println("Image: ${Constant.BASE_URL}${uri}")
        return UnitModel(
            id = id.toString(),
            carNumber = nm,
            longitude = pos?.x?:0.0,
            latitude = pos?.y?:0.0,
            estimateTime = pos?.t.toString(),
            estimateDistance = "",
            image = "${Constant.BASE_URL}${uri}",
            speed = pos?.s.toString().plus(" km/h"),
            number = nm,
            lastOnlineTime = lmsg?.t.toString(),
            isOnline = false,
            address = address
        )
    }
}