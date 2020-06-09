package com.desnyki.moviedb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Result.SUCCESS] - with data from database
 * [Result.ERROR] - if error has occurred from any source
 * [Result.LOADING]
 */

fun <T : Any, A: Any> resultLiveData(databaseQuery: () -> LiveData<T>,
                                     networkCall: suspend () -> Result<A>,
                                     saveCallResult: suspend (A) -> Unit): LiveData<Result<T>> =
        liveData(Dispatchers.IO) {
            emit(Result.Loading)
            val source: LiveData<Result<T>> = databaseQuery.invoke().map {
                    data -> Result.Success(data)
            }
            emitSource(source)
            when (val response = networkCall.invoke()){
                is Result.Success -> {
                    saveCallResult(response.data)
                }
                is Result.Error -> {
                    emit(response)
                    emitSource(source)
                }
            }
}