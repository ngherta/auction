package com.lot.mobile.domain.gateways

import com.lot.mobile.domain.entities.SettingEntity
import kotlinx.coroutines.flow.Flow

interface SettingsGateway {
    fun get(): Flow<List<SettingEntity>>
}