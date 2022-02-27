package com.lot.mobiledemodata.repositories

import com.lot.mobiledemo.domain.entities.SettingEntity
import com.lot.mobiledemo.domain.gateways.SettingsGateway
import com.lot.mobiledemodata.datasources.network.settings.SettingsNetworkSource
import com.lot.mobiledemodata.datasources.network.settings.models.SettingsNotificationNetworkModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val userRepository: UserRepository,
    private val settingsNetworkSource: SettingsNetworkSource
) : SettingsGateway {
    override fun get(): Flow<List<SettingEntity>> = flow {
        var userId = 777
        userRepository.getUser().collect { e -> userId = e.id }
        val settingsNetworkData = settingsNetworkSource.getSettings(userId)
        emit(settingsNetworkData.notifications.map(SettingsNotificationNetworkModel::toEntity))
    }
}

fun SettingsNotificationNetworkModel.toEntity(): SettingEntity {
    return SettingEntity(
        notificationType,
        name,
        description,
        value,
        userId
    )
}
