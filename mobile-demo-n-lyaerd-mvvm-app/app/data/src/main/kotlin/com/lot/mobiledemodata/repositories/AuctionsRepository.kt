package com.lot.mobiledemodata.repositories

import com.lot.mobiledemo.domain.entities.AuctionEntity
import com.lot.mobiledemo.domain.entities.UserEntity
import com.lot.mobiledemo.domain.gateways.AuctionGateway
import com.lot.mobiledemodata.datasources.disk.auctions.AuctionDiskModel
import com.lot.mobiledemodata.datasources.disk.auctions.AuctionDiskSource
import com.lot.mobiledemodata.datasources.disk.auctions.AuctionsDiskDataHolder
import com.lot.mobiledemodata.datasources.network.auctions.AuctionNetworkSource
import com.lot.mobiledemodata.datasources.network.auctions.models.AuctionNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuctionsRepository @Inject constructor(
    private val auctionDiskSource: AuctionDiskSource,
    private val auctionNetworkSource: AuctionNetworkSource
) : AuctionGateway {
    override fun getAuctions(): Flow<List<AuctionEntity>> = flow {
        val diskAuctionsData = auctionDiskSource.data?.auctions
        if (diskAuctionsData.isNullOrEmpty()) {
            val auctionNetworkData = auctionNetworkSource.getAuctions(1,5)
            auctionDiskSource.data = AuctionsDiskDataHolder(auctionNetworkData.content.map(AuctionNetworkModel::toDisk))
            emit(auctionNetworkData.content.map(AuctionNetworkModel::toEntity))
        } else {
            emit(diskAuctionsData.map(AuctionDiskModel::toEntity))
        }
    }

    override fun getAuction(id: Int) = flow {
        emit(auctionNetworkSource.getAuction(id).toEntity())
    }
}

fun AuctionDiskModel.toEntity(): AuctionEntity {
    return AuctionEntity(id,
        title,
        description,
        status,
        type,
        startPrice,
        finishPrice,
        UserEntity(user.id, user.email, user.birthday, user.firstName, user.lastName, user.enabled, user.userRole),
        startDate,
        finishDate,
        images)
}

fun AuctionNetworkModel.toEntity(): AuctionEntity {
    return AuctionEntity(id,
        title,
        description,
        status,
        type,
        startPrice,
        finishPrice,
        UserEntity(user.id, user.email, user.birthday, user.firstName, user.lastName, user.enabled, user.userRole),
        startDate,
        finishDate,
        images)
}

fun AuctionNetworkModel.toDisk(): AuctionDiskModel {
    return AuctionDiskModel(id, title, description, status, type, startPrice, finishPrice, user, startDate, finishDate, images)
}
