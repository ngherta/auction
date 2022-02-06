package com.lot.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lot.database.dao.AuctionDao
import com.lot.datamodel.AuctionDto

@Database(entities = [AuctionDto::class], version = 1)
//@TypeConverters(StatusConverter::class, OfferStatusConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun auctionDao(): AuctionDao
}
