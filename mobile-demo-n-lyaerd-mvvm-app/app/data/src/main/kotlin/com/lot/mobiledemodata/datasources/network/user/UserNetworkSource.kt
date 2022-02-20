package com.lot.mobiledemodata.datasources.network.user

import com.lot.mobiledemodata.datasources.network.user.models.UserNetworkModel
import retrofit2.http.GET
import retrofit2.http.Path

interface UserNetworkSource {
    @GET("/api/users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): UserNetworkModel
}
