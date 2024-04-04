package com.zerogravity.moonlight.mobile.native

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.zerogravity.moonlight.mobile.common.data.local.DatabaseDriverFactory
import com.zerogravity.moonlight.mobile.common.data.local.db.MoonlightMobileDb

class NativeDatabaseDriverFactory: DatabaseDriverFactory {
    override fun createDatabaseDriver(): SqlDriver {
        return NativeSqliteDriver(MoonlightMobileDb.Schema, "MoonlightMobileDb.db")
    }
}