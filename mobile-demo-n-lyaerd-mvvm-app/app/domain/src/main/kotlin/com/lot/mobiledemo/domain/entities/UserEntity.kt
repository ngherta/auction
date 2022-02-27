package com.lot.mobiledemo.domain.entities

data class UserEntity(
    val id: Int,
    val email: String,
    val birthday: String,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val userRole: Set<String>
)
