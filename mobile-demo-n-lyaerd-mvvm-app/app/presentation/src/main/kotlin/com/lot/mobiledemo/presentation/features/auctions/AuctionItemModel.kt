package com.lot.mobiledemo.presentation.features.auctions

import com.lot.mobiledemodata.datasources.network.user.models.UserNetworkModel

data class AuctionItemModel(
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
)
