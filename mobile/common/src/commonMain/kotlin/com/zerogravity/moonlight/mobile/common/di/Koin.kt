package com.zerogravity.moonlight.mobile.common.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(modules: List<Module>, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(modules)
    }

fun initKoin() = initKoin(listOf(commonModule(), dataModule(), networkModule(), repositoryModule())) {}