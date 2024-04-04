package com.zerogravity.moonlight.mobile.common.di

import com.zerogravity.moonlight.mobile.common.data.local.DatabaseDriverFactory
import com.zerogravity.moonlight.mobile.common.data.local.dao.ServiceDao
import com.zerogravity.moonlight.mobile.common.data.local.db.MoonlightMobileDb
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

fun dataModule() = module {
    single {
        val databaseDriverFactory: DatabaseDriverFactory = get()
        MoonlightMobileDb(databaseDriverFactory.createDatabaseDriver())
    }
    single { ServiceDao(get(), Dispatchers.Default) }
}