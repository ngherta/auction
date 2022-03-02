package com.lot.mobiledata.datasources.network.auctions

import com.lot.mobiledata.datasources.network.auctions.models.AuctionNetworkModel
import com.lot.mobiledata.datasources.network.auctions.models.AuctionPageNetworkModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuctionNetworkSource {
    @GET("/api/auction")
    suspend fun getAuctions(
        @Query("page") page: Int,
        @Query("perPage") perPage: Int
    ): AuctionPageNetworkModel

    @GET("/api/auction/{id}")
    suspend fun getAuction(@Path("id") auctionId: Int): AuctionNetworkModel
}
