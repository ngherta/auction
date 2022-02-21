package com.lot.mobiledemo.presentation.features.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lot.mobiledemo.domain.usecases.LoginUseCase
import com.lot.mobiledemo.presentation.infrastructure.Resource
import com.lot.mobiledemo.presentation.infrastructure.Resource.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _registerState: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val registerState: LiveData<Resource<Unit>> get() = _registerState

    fun onRegister(email: String, firstName: String, lastName: String, password: String) {
        _registerState.value = Loading()
        viewModelScope.launch {
            try {
                loginUseCase.register(email, firstName, lastName, email).collect {
                    _registerState.value = Success(Unit)
                }
            } catch (ex: IOException) {
                _registerState.value = Failure(ex)
            }
        }


    }
}
