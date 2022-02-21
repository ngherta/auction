package com.lot.mobiledemodata.repositories

import com.lot.mobiledemo.domain.entities.UserEntity
import com.lot.mobiledemo.domain.gateways.AuthGateway
import com.lot.mobiledemo.domain.models.LoginDataModel
import com.lot.mobiledemo.domain.models.RegisterDataModel
import com.lot.mobiledemodata.datasources.disk.auth.TokenDiskModel
import com.lot.mobiledemodata.datasources.disk.auth.TokenDiskSource
import com.lot.mobiledemodata.datasources.disk.user.UserDiskModel
import com.lot.mobiledemodata.datasources.disk.user.UserDiskSource
import com.lot.mobiledemodata.datasources.memmory.auth.TokenMemoryModel
import com.lot.mobiledemodata.datasources.memmory.auth.TokenMemorySource
import com.lot.mobiledemodata.datasources.memmory.user.UserMemoryModel
import com.lot.mobiledemodata.datasources.memmory.user.UserMemorySource
import com.lot.mobiledemodata.datasources.network.auth.TokenNetworkSource
import com.lot.mobiledemodata.datasources.network.auth.models.TokenNetworkModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val tokenMemorySource: TokenMemorySource,
    private val tokenDiskSource: TokenDiskSource,
    private val tokenNetworkSource: TokenNetworkSource,
    private val userMemorySource: UserMemorySource,
    private val userDiskSource: UserDiskSource
) : AuthGateway {
    override fun login(loginData: LoginDataModel) = flow {
        var token: TokenNetworkModel = tokenNetworkSource.getToken(loginData)
        tokenMemorySource.data = TokenMemoryModel(token.token)
        tokenDiskSource.data = TokenDiskModel(token.token)
        userMemorySource.data = token.userToMemory()
        userDiskSource.data = token.userToDisk()
        emit(Unit)
    }

    override fun logout() = flow {
        tokenDiskSource.data = null
        userDiskSource.data = null
        emit(Unit)
    }

    override fun register(registerData: RegisterDataModel) = flow {
        tokenNetworkSource.register(registerData)
        emit(Unit)
    }

    private fun TokenNetworkModel.userToEntity(): UserEntity {
        return UserEntity(
            userDto.id,
            userDto.email,
            userDto.birthday,
            userDto.firstName,
            userDto.lastName,
            userDto.enabled,
            userDto.userRole
        )
    }

    private fun TokenNetworkModel.userToMemory(): UserMemoryModel {
        return UserMemoryModel(
            userDto.id,
            userDto.email,
            userDto.birthday,
            userDto.firstName,
            userDto.lastName,
            userDto.enabled,
            userDto.userRole
        )
    }

    private fun TokenNetworkModel.userToDisk(): UserDiskModel {
        return UserDiskModel(
            userDto.id,
            userDto.email,
            userDto.birthday,
            userDto.firstName,
            userDto.lastName,
            userDto.enabled,
            userDto.userRole
        )
    }
}
