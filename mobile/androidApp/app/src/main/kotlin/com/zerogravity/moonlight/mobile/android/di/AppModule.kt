package com.zerogravity.moonlight.mobile.android.di

import com.zerogravity.moonlight.mobile.android.MainViewModel
import com.zerogravity.moonlight.mobile.features.authentication.GoogleAndroidAuthProvider
import com.zerogravity.moonlight.mobile.features.authentication.LoginViewModel
import com.zerogravity.moonlight.mobile.features.authentication.SignUpViewModel
import com.zerogravity.moonlight.mobile.features.home.HomeFeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun appModule() = module {
    viewModelOf(::HomeFeedViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    single { GoogleAndroidAuthProvider(get()) }
}