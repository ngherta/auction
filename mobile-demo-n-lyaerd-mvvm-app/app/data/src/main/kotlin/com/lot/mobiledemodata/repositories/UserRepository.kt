package com.lot.mobiledemodata.repositories

import com.lot.mobiledemo.domain.entities.UserEntity
import com.lot.mobiledemo.domain.gateways.UserGateway
import com.lot.mobiledemodata.datasources.disk.auth.TokenDiskModel
import com.lot.mobiledemodata.datasources.disk.auth.TokenDiskSource
import com.lot.mobiledemodata.datasources.disk.user.UserDiskModel
import com.lot.mobiledemodata.datasources.disk.user.UserDiskSource
import com.lot.mobiledemodata.datasources.memmory.auth.TokenMemoryModel
import com.lot.mobiledemodata.datasources.memmory.auth.TokenMemorySource
import com.lot.mobiledemodata.datasources.memmory.user.UserMemoryModel
import com.lot.mobiledemodata.datasources.memmory.user.UserMemorySource
import com.lot.mobiledemodata.datasources.network.auth.TokenProvider
import com.lot.mobiledemodata.datasources.network.auth.UnauthorizedException
import com.lot.mobiledemodata.datasources.network.auth.models.TokenNetworkModel
import com.lot.mobiledemodata.datasources.network.user.UserNetworkSource
import com.lot.mobiledemodata.datasources.network.user.models.UserNetworkModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userMemorySource: UserMemorySource,
    private val userDiskSource: UserDiskSource,
    private val userNetworkSource: UserNetworkSource,
    private val tokenProvider: TokenProvider,
    private val tokenMemorySource: TokenMemorySource,
    private val tokenDiskSource: TokenDiskSource
) : UserGateway {
    override fun getUser() = flow {
        emit(getFromMemory() ?: getFromDisk() ?: getFromNetwork())
    }

    private fun getFromMemory(): UserEntity? = userMemorySource.data?.toEntity()

    private fun getFromDisk(): UserEntity? {
        val data = userDiskSource.data
        userMemorySource.data = userDiskSource.data?.toMemory()
        return data?.toEntity()
    }

    private suspend fun getFromNetwork(): UserEntity {
        val token = tokenProvider.token ?: throw UnauthorizedException()
        val data = userNetworkSource.getUser(token)
        tokenDiskSource.data = TokenDiskModel(data.token)
        tokenMemorySource.data = TokenMemoryModel(data.token)
        userMemorySource.data = data.toMemory()
        userDiskSource.data = data.toDisk()
        return data.toEntity()
    }
}

private fun UserMemoryModel.toEntity(): UserEntity {
    return UserEntity(id, email, birthday, firstName, lastName, enabled, userRole)
}

private fun UserDiskModel.toEntity(): UserEntity {
    return UserEntity(id, email, birthday, firstName, lastName, enabled, userRole)
}

private fun UserDiskModel.toMemory(): UserMemoryModel {
    return UserMemoryModel(id, email, birthday, firstName, lastName, enabled, userRole)
}

private fun TokenNetworkModel.toDisk(): UserDiskModel {
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

private fun TokenNetworkModel.toMemory(): UserMemoryModel {
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

private fun TokenNetworkModel.toEntity(): UserEntity {
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

