package com.lot.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auctions")
class AuctionDto {
    @PrimaryKey
    var id: Long? = null
    var title: String? = null
    var description: String? = null
}