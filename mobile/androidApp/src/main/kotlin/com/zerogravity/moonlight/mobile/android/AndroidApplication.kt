package com.zerogravity.moonlight.mobile.android

import android.app.Application
import com.zerogravity.moonlight.mobile.android.di.appModule
import com.zerogravity.moonlight.mobile.common.di.commonModule
import com.zerogravity.moonlight.mobile.common.di.dataModule
import com.zerogravity.moonlight.mobile.common.di.domainModule
import com.zerogravity.moonlight.mobile.common.di.initKoin
import com.zerogravity.moonlight.mobile.common.di.networkModule
import com.zerogravity.moonlight.mobile.common.di.repositoryModule
import com.zerogravity.moonlight.mobile.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class AndroidApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            listOf(
                platformModule(),
                appModule(),
                networkModule(),
                commonModule(),
                dataModule(),
                repositoryModule(),
                domainModule()
            )
        ) {
            androidLogger()
            androidContext(this@AndroidApplication)

        }
    }
}