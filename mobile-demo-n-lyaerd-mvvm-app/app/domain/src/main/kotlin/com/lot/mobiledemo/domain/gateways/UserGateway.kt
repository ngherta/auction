package com.lot.mobiledemo.domain.gateways

import com.lot.mobiledemo.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserGateway {
    fun getUser(): Flow<UserEntity>
}
