package com.lot.mobile.presentation.di

import com.google.gson.Gson
import com.lot.mobiledata.datasources.network.auth.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class OkHttpClientFactory @Inject constructor() {
    fun build(vararg interceptors: Interceptor): OkHttpClient {
        val builder = newClientBuilder()
        interceptors.forEach(builder::addInterceptor)
        return builder
            .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BODY })
            .build()
    }

    private fun newClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }
}

class AnonymousRetrofitProvider @Inject constructor(
    retrofitClientBuilder: Retrofit.Builder,
    okHttpClientFactory: OkHttpClientFactory
) {
    val retrofit: Retrofit = retrofitClientBuilder
        .client(okHttpClientFactory.build())
        .build()
}

class AuthenticatedRetrofitProvider @Inject constructor(
    retrofitClientBuilder: Retrofit.Builder,
    okHttpClientFactory: OkHttpClientFactory,
    authInterceptor: AuthInterceptor
) {
    val retrofit: Retrofit = retrofitClientBuilder
        .client(okHttpClientFactory.build(authInterceptor))
        .build()
}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun providesRetrofitClientBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("http://192.168.0.103:8080/")
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    fun providesGson(): Gson = Gson()
}
