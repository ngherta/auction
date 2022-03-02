package com.lot.mobile.presentation.features.auctions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lot.mobile.domain.entities.AuctionEntity
import com.lot.mobile.domain.usecases.LoadAuctionsUseCase
import com.lot.mobile.presentation.infrastructure.ReloadableLiveData
import com.lot.mobile.presentation.infrastructure.Resource
import com.lot.mobile.presentation.infrastructure.Resource.*
import com.lot.mobile.presentation.infrastructure.asReloadableLiveData
import com.lot.mobiledata.datasources.network.user.models.UserNetworkModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuctionsViewModel @Inject constructor(
    loadAuctionsUseCase: LoadAuctionsUseCase
) : ViewModel() {
    val auctions: ReloadableLiveData<Resource<List<AuctionItemModel>>> = flow {
        emit(Loading())
        try {
            loadAuctionsUseCase.collect { auctions -> emit(Success(auctions.map { it.toUiModel() })) }
        } catch (ex: IOException) {
            emit(Failure(ex))
        }
    }.asReloadableLiveData(viewModelScope)

    init {
        auctions.load()
    }
}

private fun AuctionEntity.toUiModel(): AuctionItemModel {
    return AuctionItemModel(
        id,
        title,
        description,
        status,
        type,
        startPrice,
        finishPrice,
        UserNetworkModel(
            user.id,
            user.email,
            user.birthday,
            user.firstName,
            user.lastName,
            user.enabled,
            user.userRole
        ),
        startDate,
        finishDate,
        images
    )
}
