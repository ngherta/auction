package com.lot.mobiledemodata.datasources.network.user

import com.lot.mobiledemodata.datasources.network.auth.models.TokenNetworkModel
import retrofit2.http.GET
import retrofit2.http.Path

interface UserNetworkSource {
    @GET("/api/auth/token/{token}")
    suspend fun getUser(@Path("token") token: String): TokenNetworkModel
}
