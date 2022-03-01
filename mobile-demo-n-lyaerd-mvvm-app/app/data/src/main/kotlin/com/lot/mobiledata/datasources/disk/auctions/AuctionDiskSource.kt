package com.lot.mobiledata.datasources.disk.auctions

import android.content.SharedPreferences
import com.google.gson.Gson
import com.lot.mobiledata.datasources.disk.SharedPreferencesSource
import java.io.Serializable
import javax.inject.Inject

class AuctionDiskSource @Inject constructor(
    sharedPreferences: SharedPreferences,
    gson: Gson
) : SharedPreferencesSource<AuctionsDiskDataHolder>(
    AuctionsDiskDataHolder::class.java,
    sharedPreferences,
    gson,
    id = "auction_cache"
)

data class AuctionsDiskDataHolder(val auctions: List<AuctionDiskModel>) : Serializable
