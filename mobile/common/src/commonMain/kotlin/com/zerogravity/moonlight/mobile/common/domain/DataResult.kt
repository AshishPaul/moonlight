package com.zerogravity.moonlight.mobile.common.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart


sealed interface DataResult<out T> {
    data class Success<T>(val data: T) : DataResult<T>
    data class Error(val exception: Throwable? = null) : DataResult<Nothing>
    data object Loading : DataResult<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<DataResult<T>> =
    map<T, DataResult<T>> {
        DataResult.Success(it)
    }.onStart {
        emit(DataResult.Loading)
    }.catch {
        emit(DataResult.Error(it))
    }
