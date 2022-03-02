package com.lot.mobile.presentation.features.auctions.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lot.mobile.domain.entities.AuctionEntity
import com.lot.mobile.domain.usecases.LoadAuctionUseCase
import com.lot.mobile.presentation.infrastructure.Resource
import com.lot.mobile.presentation.infrastructure.Resource.Failure
import com.lot.mobile.presentation.infrastructure.Resource.Loading
import com.lot.mobile.presentation.infrastructure.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuctionPreviewViewModel @Inject constructor(
    private val loadAuctionUseCase: LoadAuctionUseCase
) : ViewModel() {
    private val _auctionPreview: MutableLiveData<Resource<AuctionPreviewModel>> = MutableLiveData()
    val auctionPreview: LiveData<Resource<AuctionPreviewModel>> get() = _auctionPreview

    fun loadData(auctionId: Int) {
        _auctionPreview.value = Loading()
        viewModelScope.launch {
            try {
                loadAuctionUseCase.build(auctionId).collect { auction ->
                    _auctionPreview.value = Success(auction.toUiModel())
                }
            } catch (ex: IOException) {
                _auctionPreview.value = Failure(ex)
            }
        }
    }
}

private fun AuctionEntity.toUiModel(): AuctionPreviewModel {
    return AuctionPreviewModel(title, description, status, startDate, finishDate, startPrice, finishPrice)
}

