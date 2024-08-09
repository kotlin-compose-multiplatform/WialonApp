package com.gs.wialonlocal.features.global.di

import com.gs.wialonlocal.features.global.data.repository.GlobalRepositoryImpl
import com.gs.wialonlocal.features.global.domain.repository.GlobalRepository
import com.gs.wialonlocal.features.global.domain.usecase.GlobalUseCase
import org.koin.dsl.module

val globalModule = module {
    single<GlobalRepository> { GlobalRepositoryImpl(get(), get()) }
    single { GlobalUseCase(get()) }
}