package com.lot.mobiledemo.presentation.di

import com.lot.mobiledemo.domain.gateways.AuctionGateway
import com.lot.mobiledemo.domain.gateways.AuthGateway
import com.lot.mobiledemo.domain.gateways.SettingsGateway
import com.lot.mobiledemo.domain.gateways.UserGateway
import com.lot.mobiledemodata.datasources.disk.auctions.AuctionDiskSource
import com.lot.mobiledemodata.datasources.disk.auth.TokenDiskSource
import com.lot.mobiledemodata.datasources.disk.user.UserDiskSource
import com.lot.mobiledemodata.datasources.memmory.auth.TokenMemorySource
import com.lot.mobiledemodata.datasources.memmory.user.UserMemorySource
import com.lot.mobiledemodata.datasources.network.auctions.AuctionNetworkSource
import com.lot.mobiledemodata.datasources.network.auth.TokenNetworkSource
import com.lot.mobiledemodata.datasources.network.auth.TokenProvider
import com.lot.mobiledemodata.datasources.network.settings.SettingsNetworkSource
import com.lot.mobiledemodata.datasources.network.user.UserNetworkSource
import com.lot.mobiledemodata.repositories.AuctionsRepository
import com.lot.mobiledemodata.repositories.AuthRepository
import com.lot.mobiledemodata.repositories.SettingsRepository
import com.lot.mobiledemodata.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun providesAuctionGateWay(
        diskSource: AuctionDiskSource,
        networkSource: AuctionNetworkSource
    ): AuctionGateway {
        return AuctionsRepository(diskSource, networkSource)
    }

    @Provides
    fun providesUserGateWay(
        memorySource: UserMemorySource,
        diskSource: UserDiskSource,
        networkSource: UserNetworkSource,
        tokenProvider: TokenProvider,
        tokenMemorySource: TokenMemorySource,
        tokenDiskSource: TokenDiskSource
    ): UserGateway {
        return UserRepository(memorySource, diskSource, networkSource, tokenProvider,tokenMemorySource, tokenDiskSource)
    }

    @Provides
    fun providesAuthGateWay(
        tokenMemorySource: TokenMemorySource,
        diskSource: TokenDiskSource,
        networkSource: TokenNetworkSource,
        userMemorySource: UserMemorySource,
        userDiskSource: UserDiskSource
    ): AuthGateway {
        return AuthRepository(tokenMemorySource, diskSource, networkSource, userMemorySource, userDiskSource)
    }

    @Provides
    fun providesSettingsGateWay(
        settingsNetworkSource: SettingsNetworkSource
    ): SettingsGateway {
        return SettingsRepository(settingsNetworkSource)
    }
}
