package com.lot.mobiledata.repositories

import com.lot.mobile.domain.entities.AuctionEntity
import com.lot.mobile.domain.entities.UserEntity
import com.lot.mobile.domain.gateways.AuctionGateway
import com.lot.mobiledata.datasources.disk.auctions.AuctionDiskModel
import com.lot.mobiledata.datasources.disk.auctions.AuctionDiskSource
import com.lot.mobiledata.datasources.disk.auctions.AuctionsDiskDataHolder
import com.lot.mobiledata.datasources.network.auctions.AuctionNetworkSource
import com.lot.mobiledata.datasources.network.auctions.models.AuctionNetworkModel
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
