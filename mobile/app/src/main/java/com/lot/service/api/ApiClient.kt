package com.lot.service.api

import android.content.Context
import com.lot.common.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

import java.io.File

class ApiClient (private val context: Context) {

    fun getApiService(): ApiService {
        val httpCacheDirectory = File(context.cacheDir, "offlineCache")

        //10 MB
        val cache = Cache(httpCacheDirectory, 10 * 1024 * 1024)
        val client = OkHttpClient().newBuilder()
            .cache(cache)
            .build()

        val mRetrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return mRetrofit.create(ApiService::class.java)
    }
}
