package com.lot.mobiledemodata.datasources.network.user.models

import com.google.gson.annotations.SerializedName

data class UserNetworkModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("enabled")
    val enabled: Boolean,
    @SerializedName("userRole")
    val userRole: Set<String>
)
