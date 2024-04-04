package com.zerogravity.moonlight.mobile.common.di

import com.zerogravity.moonlight.mobile.common.data.repository.AuthenticationRepository
import com.zerogravity.moonlight.mobile.common.data.repository.AuthenticationRepositoryImpl
import com.zerogravity.moonlight.mobile.common.data.repository.OfflineFirstServiceRepository
import com.zerogravity.moonlight.mobile.common.data.repository.ServiceRepository
import com.zerogravity.moonlight.mobile.common.data.repository.UserRepository
import com.zerogravity.moonlight.mobile.common.data.repository.UserRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

fun repositoryModule() = module {
    single<ServiceRepository> { OfflineFirstServiceRepository(get(), get(), get(), Dispatchers.IO) }
    single<AuthenticationRepository> { AuthenticationRepositoryImpl(get(), get(), Dispatchers.IO) }
    single<UserRepository> { UserRepositoryImpl(get(), get(), Dispatchers.IO) }
}