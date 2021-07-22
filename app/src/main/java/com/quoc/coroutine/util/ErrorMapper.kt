package com.quoc.coroutine.util

import com.quoc.coroutine.lib.IsUnauthorized
import retrofit2.HttpException

/**
 * @return Pair<Boolean,String>
 *     first: 'true' is unauthorized | 'false' is authorized
 *     second: message
 */
fun Throwable.parseMessage(): Pair<IsUnauthorized, String> {
    var isUnauthorized = false
    var msg = ""
    when (this) {
        is HttpException -> {
            when (code()) {
                401, 403 -> {
                    isUnauthorized = true
                }
                else -> {
                    msg = message ?: ""
                }
            }
        }
        else -> {
            msg = message ?: ""
        }
    }
    return Pair(isUnauthorized, msg)
}