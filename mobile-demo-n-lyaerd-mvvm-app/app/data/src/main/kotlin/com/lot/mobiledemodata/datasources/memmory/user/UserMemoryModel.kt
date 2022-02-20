package com.lot.mobiledemodata.datasources.memmory.user

data class UserMemoryModel(
    val id: Int,
    val email: String,
    val birthday: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val userRole: Set<String>
)
