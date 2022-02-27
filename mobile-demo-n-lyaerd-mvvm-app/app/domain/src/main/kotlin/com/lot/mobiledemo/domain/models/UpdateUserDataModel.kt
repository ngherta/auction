package com.lot.mobiledemo.domain.models

import java.time.LocalDate

data class UpdateUserDataModel(
    val email: String,
    val firstName: String,
    val lastName: String,
    val birthday: LocalDate
)