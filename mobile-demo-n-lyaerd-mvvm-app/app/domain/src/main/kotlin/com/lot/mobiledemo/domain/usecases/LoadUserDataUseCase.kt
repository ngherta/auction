package com.lot.mobiledemo.domain.usecases

import com.lot.mobiledemo.domain.entities.UserEntity
import com.lot.mobiledemo.domain.gateways.UserGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class LoadUserDataUseCase(
    private val userGateWay: UserGateway
) {
    fun build(): Flow<Unit> = flow {
        userGateWay.getUser().collect { emit(Unit) }
    }

    fun get(): Flow<UserEntity> = flow {
        userGateWay.getUser().collect { user -> emit(user) }
    }
}
