package com.lot.mobile.presentation.di

import com.lot.mobile.domain.gateways.AuctionGateway
import com.lot.mobile.domain.gateways.AuthGateway
import com.lot.mobile.domain.gateways.SettingsGateway
import com.lot.mobile.domain.gateways.UserGateway
import com.lot.mobiledata.datasources.disk.auctions.AuctionDiskSource
import com.lot.mobiledata.datasources.disk.auth.TokenDiskSource
import com.lot.mobiledata.datasources.disk.user.UserDiskSource
import com.lot.mobiledata.datasources.memmory.auth.TokenMemorySource
import com.lot.mobiledata.datasources.memmory.user.UserMemorySource
import com.lot.mobiledata.datasources.network.auctions.AuctionNetworkSource
import com.lot.mobiledata.datasources.network.auth.TokenNetworkSource
import com.lot.mobiledata.datasources.network.auth.TokenProvider
import com.lot.mobiledata.datasources.network.settings.SettingsNetworkSource
import com.lot.mobiledata.datasources.network.user.UserNetworkSource
import com.lot.mobiledata.repositories.AuctionsRepository
import com.lot.mobiledata.repositories.AuthRepository
import com.lot.mobiledata.repositories.SettingsRepository
import com.lot.mobiledata.repositories.UserRepository
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
        settingsNetworkSource: SettingsNetworkSource,
        userRepository: UserRepository
    ): SettingsGateway {
        return SettingsRepository(userRepository, settingsNetworkSource)
    }
}
