package com.lot.mobiledemodata.datasources.network.auth

import com.lot.mobiledemodata.addQueryParams
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class UnauthorizedException : IOException()

class AuthInterceptor @Inject constructor(
    private val tokenProvider: TokenProvider
) : Interceptor {
    override fun intercept(chain: Chain): Response {
        val originalRequest = chain.request()
        val token = tokenProvider.token ?: throw UnauthorizedException()
        val newRequest = originalRequest.addQueryParams("userId" to token)
        return chain.proceed(newRequest)
    }
}

