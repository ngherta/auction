package com.lot.mobile.presentation.di

import com.lot.mobile.domain.gateways.AuctionGateway
import com.lot.mobile.domain.gateways.AuthGateway
import com.lot.mobile.domain.gateways.SettingsGateway
import com.lot.mobile.domain.gateways.UserGateway
import com.lot.mobile.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun providesLoadAuctionsUseCase(auctionGateway: AuctionGateway): LoadAuctionsUseCase {
        return LoadAuctionsUseCase(auctionGateway)
    }

    @Provides
    fun providesLoadAuctionUseCase(auctionGateway: AuctionGateway): LoadAuctionUseCase {
        return LoadAuctionUseCase(auctionGateway)
    }

    @Provides
    fun providesLoadUserDataUseCase(userGateway: UserGateway): LoadUserDataUseCase {
        return LoadUserDataUseCase(userGateway)
    }

    @Provides
    fun providesLoginUseCase(authGateway: AuthGateway, userGateway: UserGateway): LoginUseCase {
        return LoginUseCase(authGateway, userGateway)
    }

    @Provides
    fun providesLoadSettingsUseCase(settingsGateway: SettingsGateway):LoadSettingsUseCase {
        return LoadSettingsUseCase(settingsGateway)
    }

    @Provides
    fun providesUpdateUserUseCase( userGateway: UserGateway):UpdateUserUseCase {
        return UpdateUserUseCase(userGateway)
    }
}


