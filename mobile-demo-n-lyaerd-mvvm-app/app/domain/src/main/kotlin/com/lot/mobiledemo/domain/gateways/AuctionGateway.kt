package com.lot.mobiledemo.domain.gateways

import com.lot.mobiledemo.domain.entities.AuctionEntity
import kotlinx.coroutines.flow.Flow

interface AuctionGateway {
    fun getAuctions(): Flow<List<AuctionEntity>>
    fun getAuction(id: Int): Flow<AuctionEntity>
}
