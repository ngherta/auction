package com.lot.mobiledemo.domain.usecases

import com.lot.mobiledemo.domain.gateways.UserGateway
import com.lot.mobiledemo.domain.models.RegisterDataModel
import com.lot.mobiledemo.domain.models.UpdateUserDataModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime

class UpdateUserUseCase(
    private val userGateway: UserGateway
) {
    fun update(email: String, firstName:String, lastName: String, birthday: LocalDate) = flow {
        userGateway.update(UpdateUserDataModel(email, firstName, lastName, birthday)).collect { }
        emit(Unit)
    }
}