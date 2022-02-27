package com.lot.mobiledemodata.datasources.network.auth

import com.lot.mobiledemo.domain.models.LoginDataModel
import com.lot.mobiledemo.domain.models.RegisterDataModel
import com.lot.mobiledemodata.datasources.network.auth.models.TokenNetworkModel
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenNetworkSource {
    @POST("/api/auth/signin")
    suspend fun getToken(@Body loginRequest: LoginDataModel): TokenNetworkModel

    @POST("api/auth/signup")
    suspend fun register(@Body registerData: RegisterDataModel)
}
