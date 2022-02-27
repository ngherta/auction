package com.lot.mobiledata.datasources.network.auth.models

import com.google.gson.annotations.SerializedName
import com.lot.mobiledata.datasources.memmory.auth.UserNetworkModel

data class TokenNetworkModel(
    @SerializedName("token")
    val token: String,
    @SerializedName("userDto")
    val userDto: UserNetworkModel
)
