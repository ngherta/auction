package com.lot.service.api

import com.lot.common.Constants
import com.lot.response.LoginResponse
import retrofit2.Call
import retrofit2.http.*

/**
 * Interface for defining REST request methods
 */
interface ApiService {

    @POST(Constants.LOGIN_URL)
    @FormUrlEncoded
    fun login(
        @Header("Authorization") basic: String,
        @Field("username") email: String,
        @Field("password") password: String,
        @Field("grant_type") grantType: String
    ): Call<LoginResponse>

}