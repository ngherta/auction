package com.lot.mobile.presentation.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lot.mobile.domain.usecases.LoginUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _authenticationState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val authenticationState: LiveData<Resource<Unit>> get() = _authenticationState

    fun onLogin(email: String, password: String) {
        _authenticationState.value = Loading()
        viewModelScope.launch {
            try {
                loginUseCase.login(email, password).collect {
                    _authenticationState.value = Success(Unit)
                }
            } catch (ex: IOException) {
                _authenticationState.value = Failure(ex)
            }
        }
    }
}
