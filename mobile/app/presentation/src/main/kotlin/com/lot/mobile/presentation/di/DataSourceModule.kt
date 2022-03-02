package com.lot.mobile.presentation.di

import com.lot.mobiledata.datasources.network.auctions.AuctionNetworkSource
import com.lot.mobiledata.datasources.network.auth.TokenNetworkSource
import com.lot.mobiledata.datasources.network.settings.SettingsNetworkSource
import com.lot.mobiledata.datasources.network.user.UserNetworkSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    /******************************************************************************
     * Anonymous
     *****************************************************************************/
    @Provides
    fun providesUserNetworkSource(
        retrofitProvider: AnonymousRetrofitProvider
    ): UserNetworkSource {
        return retrofitProvider.retrofit.create(UserNetworkSource::class.java)
    }

    @Provides
    fun providesTokenNetworkSource(
        retrofitProvider: AnonymousRetrofitProvider
    ): TokenNetworkSource {
        return retrofitProvider.retrofit.create(TokenNetworkSource::class.java)
    }

    @Provides
    fun providesSettingNetworkSource(
        retrofitProvider: AnonymousRetrofitProvider
    ): SettingsNetworkSource {
        return retrofitProvider.retrofit.create(SettingsNetworkSource::class.java)
    }

    /******************************************************************************
     * Authenticated
     *****************************************************************************/
    @Provides
    fun providesAuctionNetworkSource(
        retrofitProvider: AuthenticatedRetrofitProvider
    ): AuctionNetworkSource {
        return retrofitProvider.retrofit.create(AuctionNetworkSource::class.java)
    }
}
