package com.zerogravity.moonlight.mobile

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.module.Module

expect fun platformName(): String

expect fun platformModule(): Module

expect class CommonFlow<T>(flow: Flow<T>): Flow<T>

expect open class CommonMutableStateFlow<T>(flow: MutableStateFlow<T>) : MutableStateFlow<T>

expect open class CommonStateFlow<T>(flow: StateFlow<T>) : StateFlow<T>
