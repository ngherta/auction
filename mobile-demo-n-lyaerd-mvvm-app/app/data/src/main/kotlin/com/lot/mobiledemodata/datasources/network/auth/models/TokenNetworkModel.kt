package com.lot.mobiledemodata.datasources.network.auth.models

import com.google.gson.annotations.SerializedName
import com.lot.mobiledemodata.datasources.memmory.auth.UserNetworkModel

data class TokenNetworkModel(
    @SerializedName("token")
    val token: String,
    @SerializedName("userDto")
    val userDto: UserNetworkModel
)
