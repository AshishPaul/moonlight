package com.zerogravity.moonlight.mobile.common.di

import com.zerogravity.moonlight.mobile.common.domain.usecase.LoginUseCase
import com.zerogravity.moonlight.mobile.common.domain.usecase.SignUpUseCase
import com.zerogravity.moonlight.mobile.common.domain.JwtTokenDecoder
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun domainModule() = module {
    singleOf(::LoginUseCase)
    singleOf(::SignUpUseCase)
    singleOf(::JwtTokenDecoder)
}