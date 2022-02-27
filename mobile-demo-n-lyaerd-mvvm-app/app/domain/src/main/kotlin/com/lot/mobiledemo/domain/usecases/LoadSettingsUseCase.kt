package com.lot.mobiledemo.domain.usecases

import com.lot.mobiledemo.domain.entities.SettingEntity
import com.lot.mobiledemo.domain.gateways.SettingsGateway
import kotlinx.coroutines.flow.Flow

class LoadSettingsUseCase(
    private val settingsGateway: SettingsGateway
) : Flow<List<SettingEntity>> by settingsGateway.get()
