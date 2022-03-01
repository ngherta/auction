package com.lot.mobiledata.datasources.network.auth

import com.lot.mobiledata.addQueryParams
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
        val token = tokenProvider.token ?: throw UnauthorizedException()
        val newRequest = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", token)
            .build()
        return chain.proceed(newRequest)
    }
}

