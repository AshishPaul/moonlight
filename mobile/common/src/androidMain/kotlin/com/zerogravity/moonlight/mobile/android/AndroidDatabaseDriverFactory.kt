package com.zerogravity.moonlight.mobile.android

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.zerogravity.moonlight.mobile.common.data.local.DatabaseDriverFactory
import com.zerogravity.moonlight.mobile.common.data.local.db.MoonlightMobileDb

class AndroidDatabaseDriverFactory(private val context: Context) : DatabaseDriverFactory {
    override fun createDatabaseDriver(): SqlDriver {
        return AndroidSqliteDriver(MoonlightMobileDb.Schema, context, "MoonlightMobileDb.db")
    }
}