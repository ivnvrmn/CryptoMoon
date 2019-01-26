package com.rmnivnv.cryptomoon.model.network

import retrofit2.Response
import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>, errorMessage: String): T? {
        return when (val result = safeApiResult(call, errorMessage)) {
            is Result.Success -> result.data
            is Result.Error -> null //todo handle error
        }
    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Result<T> {
        return call.invoke().let { response ->
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(IOException(errorMessage))
            }
        }
    }
}
