package com.desnyki.moviedb.api

import retrofit2.Response
import com.desnyki.moviedb.data.Result

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

//    protected suspend fun <T: Any> getResult(call: suspend () -> Deferred<T>): Result<T> {
    protected suspend fun <T: Any> getResult(call: suspend () -> Response<T>): Result<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Result.Success(body)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T: Any> error(message: String): Result<T> {
//        Timber.e(message)
        return Result.Error(Exception("Network call has failed for a following reason: $message"))
    }

}

