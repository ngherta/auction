package com.lot.mobile.presentation.features.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lot.mobile.domain.entities.UserEntity
import com.lot.mobile.domain.usecases.LoadUserDataUseCase
import com.lot.mobile.domain.usecases.LoginUseCase
import com.lot.mobile.domain.usecases.UpdateUserUseCase
import com.lot.mobile.presentation.infrastructure.ReloadableLiveData
import com.lot.mobile.presentation.infrastructure.Resource
import com.lot.mobile.presentation.infrastructure.Resource.*
import com.lot.mobile.presentation.infrastructure.asReloadableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val loadUserDataUseCase: LoadUserDataUseCase,
    private val loginUseCase: LoginUseCase
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


    @RequiresApi(Build.VERSION_CODES.O)
    fun onUpdate(email: String, firstName:String, lastName:String) {
        _updateState.value = Loading()
        viewModelScope.launch {
            try {
                val birthday = LocalDate.of(2000, 1, 2)
                updateUserUseCase.update(email, firstName, lastName, birthday).collect {
                    _updateState.value = Success(Unit)
                }
            } catch (ex: IOException) {
                _updateState.value = Failure(ex)
            }
        }
    }

    fun logout() {
        loginUseCase.logout()
    }
}

private fun UserEntity.toUiModel(): UserModel {
    return UserModel(
        email,
        firstName,
        lastName
    )
}
