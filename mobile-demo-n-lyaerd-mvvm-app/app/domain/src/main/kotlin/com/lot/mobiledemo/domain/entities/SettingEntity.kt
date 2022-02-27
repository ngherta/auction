package com.lot.mobiledemo.domain.entities

data class SettingEntity (
    val notificationType: String,
    val name: String,
    val description: String,
    val value: Boolean,
    val userId: Long
        )