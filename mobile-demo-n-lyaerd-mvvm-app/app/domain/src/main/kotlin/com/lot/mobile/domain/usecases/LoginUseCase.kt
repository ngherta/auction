package com.lot.mobile.domain.usecases

import com.lot.mobile.domain.gateways.AuthGateway
import com.lot.mobile.domain.gateways.UserGateway
import com.lot.mobile.domain.models.LoginDataModel
import com.lot.mobile.domain.models.RegisterDataModel
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

    fun logout() = flow {
        authGateway.logout()
        emit(Unit)
    }
}
