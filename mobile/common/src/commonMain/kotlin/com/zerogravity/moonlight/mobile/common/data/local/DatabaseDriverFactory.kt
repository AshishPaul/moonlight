package com.zerogravity.moonlight.mobile.common.data.local

import app.cash.sqldelight.db.SqlDriver

interface DatabaseDriverFactory {
    fun createDatabaseDriver(): SqlDriver
}