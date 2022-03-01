package com.lot.mobiledata.datasources.network.settings

import com.lot.mobiledata.datasources.network.settings.models.SettingsNetworkModel
import com.lot.mobiledata.datasources.network.settings.models.SettingsNotificationNetworkModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SettingsNetworkSource {
    @GET("/api/settings/{userId}")
    suspend fun getSettings(@Path("userId") userId: Int): SettingsNetworkModel

//    @POST("/api/settings/notification")
//    suspend fun updateNotificationSettings()
}