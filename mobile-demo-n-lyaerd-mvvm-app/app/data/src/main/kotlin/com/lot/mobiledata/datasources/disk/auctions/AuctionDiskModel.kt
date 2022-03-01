package com.lot.mobiledata.datasources.disk.auctions

import com.lot.mobiledata.datasources.memmory.auth.UserNetworkModel
import java.io.Serializable

data class AuctionDiskModel(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val type: String,
    val startPrice: Double,
    val finishPrice: Double,
    val user: UserNetworkModel,
    val startDate: String,
    val finishDate: String,
    val images: List<String>
) : Serializable
