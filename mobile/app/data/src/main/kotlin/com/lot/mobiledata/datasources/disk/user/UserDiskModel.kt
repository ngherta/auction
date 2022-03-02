package com.lot.mobiledata.datasources.disk.user

import java.io.Serializable

data class UserDiskModel(
    val id: Int,
    val email: String,
    val birthday: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val userRole: Set<String>
) : Serializable
