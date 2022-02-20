package com.lot.mobiledemodata.repositories

import com.lot.mobiledemo.domain.entities.UserEntity
import com.lot.mobiledemo.domain.gateways.UserGateway
import com.lot.mobiledemodata.datasources.disk.user.UserDiskModel
import com.lot.mobiledemodata.datasources.disk.user.UserDiskSource
import com.lot.mobiledemodata.datasources.memmory.user.UserMemoryModel
import com.lot.mobiledemodata.datasources.memmory.user.UserMemorySource
import com.lot.mobiledemodata.datasources.network.auth.TokenProvider
import com.lot.mobiledemodata.datasources.network.auth.UnauthorizedException
import com.lot.mobiledemodata.datasources.network.user.UserNetworkSource
import com.lot.mobiledemodata.datasources.network.user.models.UserNetworkModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userMemorySource: UserMemorySource,
    private val userDiskSource: UserDiskSource,
    private val userNetworkSource: UserNetworkSource,
    private val tokenProvider: TokenProvider
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
        userMemorySource.data = data.toMemory()
        userDiskSource.data = data.toDisk()
        return data.toEntity()
    }
}

private fun UserMemoryModel.toEntity(): UserEntity {
    return UserEntity(id,email,birthday,firstName,lastName, enabled, userRole)
}

private fun UserDiskModel.toEntity(): UserEntity {
    return UserEntity(id,email,birthday,firstName,lastName, enabled, userRole)
}

private fun UserDiskModel.toMemory(): UserMemoryModel {
    return UserMemoryModel(id, email, birthday, firstName, lastName, enabled, userRole)
}

private fun UserNetworkModel.toDisk(): UserDiskModel {
    return UserDiskModel(id, email, birthday, firstName, lastName, enabled, userRole)
}

private fun UserNetworkModel.toMemory(): UserMemoryModel {
    return UserMemoryModel(id, email, birthday, firstName, lastName, enabled, userRole)
}

private fun UserNetworkModel.toEntity(): UserEntity {
    return UserEntity(id, email, birthday, firstName, lastName, enabled, userRole)
}

