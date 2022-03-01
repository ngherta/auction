package com.lot.mobile.domain.usecases

import com.lot.mobile.domain.entities.SettingEntity
import com.lot.mobile.domain.gateways.SettingsGateway
import kotlinx.coroutines.flow.Flow

class LoadSettingsUseCase(
    private val settingsGateway: SettingsGateway
) : Flow<List<SettingEntity>> by settingsGateway.get()
