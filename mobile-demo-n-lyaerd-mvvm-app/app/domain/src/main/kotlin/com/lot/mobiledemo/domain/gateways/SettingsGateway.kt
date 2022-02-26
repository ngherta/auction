package com.lot.mobiledemo.domain.gateways

import com.lot.mobiledemo.domain.entities.SettingEntity
import kotlinx.coroutines.flow.Flow

interface SettingsGateway {
    fun get(): Flow<List<SettingEntity>>
}