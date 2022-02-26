package com.lot.mobiledemo.domain.gateways

import com.lot.mobiledemo.domain.entities.UserEntity
import com.lot.mobiledemo.domain.models.RegisterDataModel
import kotlinx.coroutines.flow.Flow

interface UserGateway {
    fun getUser(): Flow<UserEntity>
    fun update(registerData: RegisterDataModel): Flow<UserEntity>
}
