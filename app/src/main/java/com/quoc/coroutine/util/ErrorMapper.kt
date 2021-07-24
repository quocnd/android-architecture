package com.quoc.coroutine.util

import android.content.Context
import com.quoc.coroutine.R
import com.quoc.coroutine.lib.IsUnauthorized
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.UnknownHostException

/**
 * @return Pair<Boolean,String>
 *     first: 'true' is unauthorized | 'false' is authorized
 *     second: message
 */
fun Throwable.parseMessage(context: Context): Pair<IsUnauthorized, String> {
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
        is UnknownHostException, is InterruptedIOException ->{
            msg = context.getString(R.string.no_internet_connection)
        }
        else -> {
            msg = message ?: ""
        }
    }
    return Pair(isUnauthorized, msg)
}