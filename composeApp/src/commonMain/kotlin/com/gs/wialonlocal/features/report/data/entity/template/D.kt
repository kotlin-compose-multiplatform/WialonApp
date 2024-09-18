package com.gs.wialonlocal.features.report.data.entity.template

import kotlinx.serialization.Serializable

@Serializable
data class D(
    val cls: Int,
    val drvrs: Drvrs,
    val drvrsmax: Int,
    val id: Int,
    val mu: Int,
    val nm: String,
    val rep: Map<String, Rep>,
    val uacl: Long
)