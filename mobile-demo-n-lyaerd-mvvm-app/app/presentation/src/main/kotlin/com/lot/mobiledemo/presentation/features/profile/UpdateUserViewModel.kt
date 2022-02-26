package com.lot.mobiledemo.presentation.features.profile

import androidx.lifecycle.*
import com.lot.mobiledemo.domain.entities.SettingEntity
import com.lot.mobiledemo.domain.entities.UserEntity
import com.lot.mobiledemo.domain.usecases.LoadUserDataUseCase
import com.lot.mobiledemo.domain.usecases.UpdateUserUseCase
import com.lot.mobiledemo.presentation.features.profile.settings.SettingItemModel
import com.lot.mobiledemo.presentation.infrastructure.ReloadableLiveData
import com.lot.mobiledemo.presentation.infrastructure.Resource
import com.lot.mobiledemo.presentation.infrastructure.Resource.*
import com.lot.mobiledemo.presentation.infrastructure.asReloadableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val loadUserDataUseCase: LoadUserDataUseCase
) : ViewModel() {
    val userData: ReloadableLiveData<Resource<UserEntity>> = flow {
        try {
            emit(Loading())
            loadUserDataUseCase.get().collect { user -> emit(Success(user)) }
        } catch (ex: IOException) {
            emit(Failure(ex))
        }
    }.asReloadableLiveData(viewModelScope)

    init {
        userData.load()
    }



    private val _updateState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val authenticationState: LiveData<Resource<Unit>> get() = _updateState

    fun onUpdate(email: String, firstName:String, lastName:String) {
        _updateState.value = Loading()
        viewModelScope.launch {
            try {
                updateUserUseCase.update(email, firstName, lastName, "").collect {
                    _updateState.value = Success(Unit)
                }
            } catch (ex: IOException) {
                _updateState.value = Failure(ex)
            }
        }
    }
}

private fun UserEntity.toUiModel(): UserModel {
    return UserModel(
        email,
        firstName,
        lastName
    )
}
