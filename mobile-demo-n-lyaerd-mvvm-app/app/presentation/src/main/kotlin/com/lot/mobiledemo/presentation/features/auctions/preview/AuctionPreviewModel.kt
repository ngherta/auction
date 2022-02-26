package com.lot.mobiledemo.presentation.features.auctions.preview

data class AuctionPreviewModel(
    val title: String,
    val description: String,
    val status:String,
    val startDate: String,
    val finishDate: String,
    val startPrice: Double,
    val finishPrice: Double,

)
