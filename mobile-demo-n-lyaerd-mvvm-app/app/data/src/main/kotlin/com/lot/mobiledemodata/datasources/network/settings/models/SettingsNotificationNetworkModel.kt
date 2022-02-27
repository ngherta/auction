package com.lot.mobiledemodata.datasources.network.settings.models

import com.google.gson.annotations.SerializedName

data class SettingsNotificationNetworkModel (
    @SerializedName("notificationType")
    val notificationType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("value")
    val value: Boolean,
    @SerializedName("userId")
    val userId: Long
    )