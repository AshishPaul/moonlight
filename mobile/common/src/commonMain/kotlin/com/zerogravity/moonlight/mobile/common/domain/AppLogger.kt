package com.zerogravity.moonlight.mobile.common.domain

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.loggerConfigInit
import com.zerogravity.moonlight.mobile.platformName

interface AppLogger {
    fun i(message: String)
    fun d(message: String)
    fun e(throwable: Throwable? = null, message: String? = null)
}

class KermitLogger(logWriter: LogWriter) : AppLogger {

    private val logger: Logger

    init {
        logger = Logger(loggerConfigInit(logWriter))
    }

    override fun i(message: String) {
        logger.i(tag = platformName()) { message }
    }

    override fun d(message: String) {
        logger.d(tag = platformName()) { message }
    }

    override fun e(throwable: Throwable?, message: String?) {
        logger.e(tag = platformName(), throwable = throwable) { message ?: throwable?.message ?: "" }
    }
}