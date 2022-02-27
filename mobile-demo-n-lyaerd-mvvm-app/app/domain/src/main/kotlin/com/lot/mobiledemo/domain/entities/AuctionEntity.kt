package com.lot.mobiledemo.domain.entities

data class AuctionEntity(
    val id: Int,
    val title: String,
    val description: String,
    val status: String,
    val type: String,
    val startPrice: Double,
    val finishPrice: Double,
    val user: UserEntity,
    val startDate: String,
    val finishDate: String,
    val images: List<String>
)
