package com.lot.mobile.domain.gateways

import com.lot.mobile.domain.entities.AuctionEntity
import kotlinx.coroutines.flow.Flow

interface AuctionGateway {
    fun getAuctions(): Flow<List<AuctionEntity>>
    fun getAuction(id: Int): Flow<AuctionEntity>
}
