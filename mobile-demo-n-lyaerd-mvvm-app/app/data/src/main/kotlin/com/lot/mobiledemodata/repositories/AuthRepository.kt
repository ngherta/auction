package com.lot.mobiledemodata.repositories

import com.lot.mobiledemo.domain.gateways.AuthGateway
import com.lot.mobiledemo.domain.gateways.UserGateway
import com.lot.mobiledemo.domain.models.LoginDataModel
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
import retrofit2.HttpException
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
        tokenMemorySource.data = TokenMemoryModel(token.id.toString())
        tokenDiskSource.data = TokenDiskModel(token.id.toString())
//        userMemorySource.data =
//            UserMemoryModel(
//                token.userDto.id,
//                token.userDto.email,
//                token.userDto.birthday,
//                token.userDto.firstName,
//                token.userDto.lastName,
//                token.userDto.enabled,
//                token.userDto.userRole
//            )
//        userDiskSource.data = UserDiskModel(
//            token.userDto.id,
//            token.userDto.email,
//            token.userDto.birthday,
//            token.userDto.firstName,
//            token.userDto.lastName,
//            token.userDto.enabled,
//            token.userDto.userRole
//        )
        emit(Unit)
    }

    override fun logout() = flow {
        tokenDiskSource.data = null
        emit(Unit)
    }
}
