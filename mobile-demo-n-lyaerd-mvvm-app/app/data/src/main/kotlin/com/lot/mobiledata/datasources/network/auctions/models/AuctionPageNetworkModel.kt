package com.lot.mobiledata.datasources.network.auctions.models

import com.google.gson.annotations.SerializedName

data class AuctionPageNetworkModel (
    @SerializedName("content")
    val content: List<AuctionNetworkModel>,
    @SerializedName("totalPages")
    val pages: Int
)