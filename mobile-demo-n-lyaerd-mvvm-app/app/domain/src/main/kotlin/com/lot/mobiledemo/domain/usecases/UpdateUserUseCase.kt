package com.lot.mobiledemo.domain.usecases

import com.lot.mobiledemo.domain.gateways.UserGateway
import com.lot.mobiledemo.domain.models.RegisterDataModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UpdateUserUseCase(
    private val userGateway: UserGateway
) {
    fun update(email: String, firstName:String, lastName: String, password: String) = flow {
        userGateway.update(RegisterDataModel(email, firstName, lastName, password)).collect { }
        emit(Unit)
    }
}