package com.rmnivnv.cryptomoon.model.network

import java.io.IOException

open class BaseRepository {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Result<T>,
        errorMessage: String
    ): Result<T> = try {
        call.invoke()
    } catch (exception: Exception) {
        Result.Error(IOException(errorMessage, exception))
    }
}
