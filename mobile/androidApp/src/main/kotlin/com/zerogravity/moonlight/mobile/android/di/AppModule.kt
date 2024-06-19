package com.zerogravity.moonlight.mobile.android.di

import com.zerogravity.moonlight.mobile.android.presentation.authentication.GoogleAndroidAuthProvider
import com.zerogravity.moonlight.mobile.android.presentation.authentication.LoginViewModel
import com.zerogravity.moonlight.mobile.android.presentation.authentication.SignUpViewModel
import com.zerogravity.moonlight.mobile.android.presentation.homefeed.HomeFeedViewModel
import com.zerogravity.moonlight.mobile.android.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun appModule() = module {
    viewModelOf(::HomeFeedViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::MainViewModel)
    single { GoogleAndroidAuthProvider(get()) }
}