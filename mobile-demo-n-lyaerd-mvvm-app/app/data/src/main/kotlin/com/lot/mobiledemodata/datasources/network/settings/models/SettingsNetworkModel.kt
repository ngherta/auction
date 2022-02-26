package com.lot.mobiledemodata.datasources.network.settings.models

import com.google.gson.annotations.SerializedName

data class SettingsNetworkModel(
    @SerializedName("notifications")
    val notifications: List<SettingsNotificationNetworkModel>
)