package com.gs.wialonlocal.features.global.domain.usecase

import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.global.domain.model.CheckSessionModel
import com.gs.wialonlocal.features.global.domain.repository.GlobalRepository
import kotlinx.coroutines.flow.Flow

class GlobalUseCase(
    private val repository: GlobalRepository
) {
    suspend fun checkSession(): Flow<Resource<CheckSessionModel>> {
        return repository.checkSession()
    }
}