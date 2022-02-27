package com.lot.mobile.presentation.features.startup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lot.mobile.domain.usecases.LoadUserDataUseCase
import com.lot.mobile.presentation.infrastructure.Resource
import com.lot.mobile.presentation.infrastructure.Resource.Failure
import com.lot.mobile.presentation.infrastructure.Resource.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class StartupViewModel @Inject constructor(
    private val loadUserDataUseCase: LoadUserDataUseCase
) : ViewModel() {
    val userDataLoadingState: LiveData<Resource<Unit>> = flow {
        try {
            loadUserDataUseCase.build().collect {}
            emit(Success(Unit))
        } catch (ex: IOException) {
            emit(Failure(ex))
        }
    }.asLiveData(viewModelScope.coroutineContext)
}
