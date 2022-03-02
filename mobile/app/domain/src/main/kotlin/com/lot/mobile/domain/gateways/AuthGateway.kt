package com.lot.mobile.domain.gateways

import com.lot.mobile.domain.models.LoginDataModel
import com.lot.mobile.domain.models.RegisterDataModel
import kotlinx.coroutines.flow.Flow

interface AuthGateway {
    fun login(loginData: LoginDataModel): Flow<Unit>
    fun register(registerData: RegisterDataModel): Flow<Unit>
    fun logout(): Flow<Unit>
}
