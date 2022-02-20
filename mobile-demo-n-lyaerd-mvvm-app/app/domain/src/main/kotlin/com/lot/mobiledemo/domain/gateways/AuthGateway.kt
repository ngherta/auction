package com.lot.mobiledemo.domain.gateways

import com.lot.mobiledemo.domain.models.LoginDataModel
import kotlinx.coroutines.flow.Flow

interface AuthGateway {
    fun login(loginData: LoginDataModel): Flow<Unit>
    fun logout(): Flow<Unit>
}
