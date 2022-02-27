package com.lot.mobiledemo.presentation.features.profile.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lot.mobiledemo.domain.entities.SettingEntity
import com.lot.mobiledemo.domain.usecases.LoadSettingsUseCase
import com.lot.mobiledemo.presentation.infrastructure.ReloadableLiveData
import com.lot.mobiledemo.presentation.infrastructure.Resource
import com.lot.mobiledemo.presentation.infrastructure.Resource.*
import com.lot.mobiledemo.presentation.infrastructure.asReloadableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    loadSettingsUseCase: LoadSettingsUseCase
) : ViewModel() {
    val settings: ReloadableLiveData<Resource<List<SettingItemModel>>> = flow {
        emit(Loading())
        try {
            loadSettingsUseCase.collect { settings -> emit(Success(settings.map { it.toUiModel() })) }
        } catch (ex: IOException) {
            emit(Failure(ex))
        }
    }.asReloadableLiveData(viewModelScope)

    init {
        settings.load()
    }
}

private fun SettingEntity.toUiModel(): SettingItemModel {
    return SettingItemModel(
        notificationType,
        name,
        description,
        value,
        userId
    )
}
