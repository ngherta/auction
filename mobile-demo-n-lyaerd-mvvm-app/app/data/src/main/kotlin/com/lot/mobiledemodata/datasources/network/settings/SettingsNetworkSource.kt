package com.lot.mobiledemodata.datasources.network.settings

import com.lot.mobiledemodata.datasources.network.settings.models.SettingsNetworkModel
import com.lot.mobiledemodata.datasources.network.settings.models.SettingsNotificationNetworkModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SettingsNetworkSource {
    @GET("/api/settings/{userId}")
    suspend fun getSettings(@Path("userId") userId: Long): SettingsNetworkModel

//    @POST("/api/settings/notification")
//    suspend fun updateNotificationSettings()
}