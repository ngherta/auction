package com.lot.database.dao

import androidx.room.*
import com.lot.datamodel.AuctionDto

@Dao
interface AuctionDao {

    @Query("SELECT * FROM auctions LIMIT :limit OFFSET :offset")
    suspend fun get(offset: Int, limit: Int): List<AuctionDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(offers: List<AuctionDto>)

    @Delete
    fun delete(offer: AuctionDto)

    @Query("DELETE FROM auctions")
    fun deleteAll()
}