package com.lot.mobiledata.datasources.network.user

import com.lot.mobile.domain.models.UpdateUserDataModel
import com.lot.mobiledata.datasources.network.auth.models.TokenNetworkModel
import com.lot.mobiledata.datasources.network.user.models.UserNetworkModel
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserNetworkSource {
    @GET("/api/auth/token/{token}")
    suspend fun getUser(@Path("token") token: String): TokenNetworkModel

    @PUT("/api/users")
    suspend fun updateUser(request: UpdateUserDataModel): UserNetworkModel
}
