package com.gs.wialonlocal.features.global.domain.repository

import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.global.domain.model.CheckSessionModel
import kotlinx.coroutines.flow.Flow

interface GlobalRepository {
    suspend fun checkSession(): Flow<Resource<CheckSessionModel>>
}