package com.gs.wialonlocal.features.report.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TemplateModel(
    val id: String,
    val name: String
)
