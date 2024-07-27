package com.gs.wialonlocal.features.report.data.repository

import com.gs.wialonlocal.core.network.Resource
import com.gs.wialonlocal.features.report.domain.model.TemplateModel
import com.gs.wialonlocal.features.report.domain.repository.ReportRepository
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReportRepositoryImpl(
    private val httpClient: HttpClient
): ReportRepository {
    override suspend fun getTemplates(): Flow<Resource<List<TemplateModel>>> = flow {
        try {
            emit(Resource.Loading())
            delay(3000L)
            emit(Resource.Success(
                data = List(30) { index->
                    TemplateModel(
                        id = index.toString(),
                        name = "Arcabil"
                    )
                }
            ))
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(Resource.Error(ex.message))
        }
    }
}