package com.lot.mobiledemo.presentation.features.profile.settings

data class SettingItemModel(
    val notificationType: String,
    val name: String,
    val description: String,
    val value: Boolean,
    val userId: Long
)