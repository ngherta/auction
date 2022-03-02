package com.lot.mobiledata.datasources.network.auctions.models

import com.google.gson.annotations.SerializedName
import com.lot.mobiledata.datasources.memmory.auth.UserNetworkModel

data class AuctionNetworkModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("statusType")
    val status: String,
    @SerializedName("auctionType")
    val type: String,
    @SerializedName("startPrice")
    val startPrice: Double,
    @SerializedName("finishPrice")
    val finishPrice: Double,
    @SerializedName("user")
    val user: UserNetworkModel,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("finishDate")
    val finishDate: String,
    @SerializedName("images")
    val images: List<String>
)
