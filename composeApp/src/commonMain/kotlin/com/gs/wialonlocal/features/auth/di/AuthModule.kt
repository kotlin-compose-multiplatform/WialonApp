package com.gs.wialonlocal.features.auth.di

import com.gs.wialonlocal.features.auth.data.AuthSettings
import com.russhwolf.settings.Settings
import org.koin.dsl.module

val authModule = module {
    single { Settings() }
    single { AuthSettings(get()) }
}