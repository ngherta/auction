package com.lot.mobiledemo.domain.usecases

import com.lot.mobiledemo.domain.entities.AuctionEntity
import com.lot.mobiledemo.domain.gateways.AuctionGateway
import kotlinx.coroutines.flow.Flow

class LoadAuctionsUseCase(
    private val auctionGateway: AuctionGateway
) : Flow<List<AuctionEntity>> by auctionGateway.getAuctions()

class LoadAuctionUseCase(private val auctionGateway: AuctionGateway) {
    fun build(id: Int): Flow<AuctionEntity> = auctionGateway.getAuction(id)
}
