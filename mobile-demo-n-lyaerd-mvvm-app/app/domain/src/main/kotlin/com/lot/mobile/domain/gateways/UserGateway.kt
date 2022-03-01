package com.lot.mobile.domain.gateways

import com.lot.mobile.domain.entities.UserEntity
import com.lot.mobile.domain.models.RegisterDataModel
import com.lot.mobile.domain.models.UpdateUserDataModel
import kotlinx.coroutines.flow.Flow

interface UserGateway {
    fun getUser(): Flow<UserEntity>
    fun update(updateData: UpdateUserDataModel): Flow<UserEntity>
}
