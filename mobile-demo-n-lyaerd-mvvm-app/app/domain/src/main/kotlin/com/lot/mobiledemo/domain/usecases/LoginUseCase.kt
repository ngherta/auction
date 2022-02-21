package com.lot.mobiledemo.domain.usecases

import com.lot.mobiledemo.domain.gateways.AuthGateway
import com.lot.mobiledemo.domain.gateways.UserGateway
import com.lot.mobiledemo.domain.models.LoginDataModel
import com.lot.mobiledemo.domain.models.RegisterDataModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoginUseCase(
    private val authGateway: AuthGateway,
    private val userGateway: UserGateway
) {
    fun login(email: String, password: String) = flow {
        authGateway.login(LoginDataModel(email, password)).collect { }
//        userGateway.getUser().collect { }
        emit(Unit)
    }
    fun register(email: String, firstName:String, lastName: String, password: String) = flow {
        authGateway.register(RegisterDataModel(email, firstName, lastName, password)).collect { }
        emit(Unit)
    }
}
