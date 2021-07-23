package com.quoc.coroutine.domain.lib

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 * @param <RequestType>
 * @param <ResultType>
 */
abstract class NetworkBoundResource<RequestType, ResultType> {

    suspend fun execute(): Flow<Result<ResultType>> {
        return flow {
            emit(Result.Loading)
            try {
                val data = loadFromDb()
                preFetch(data)
                if (shouldFetch(data)) {
                    val result = createCall()
                    if (shouldClearCurrentData()) {
                        clearCurrentData()
                    }
                    saveCallResult(result)
                    emit(Result.Success(loadFromDb()!!))
                } else {
                    if (data != null) {
                        emit(Result.Success(data))
                    } else {
                        emit(Result.Error(Exception("There is no data")))
                    }
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
                emit(Result.Error(exception))
            }
        }
    }

    protected open suspend fun preFetch(data: ResultType?) = Unit

    protected open suspend fun shouldFetch(data: ResultType?): Boolean = true

    protected open suspend fun shouldClearCurrentData() = false

    protected open suspend fun clearCurrentData() = Unit

    protected abstract suspend fun loadFromDb(): ResultType?

    protected abstract suspend fun createCall(): RequestType

    protected abstract suspend fun saveCallResult(result: RequestType)

}