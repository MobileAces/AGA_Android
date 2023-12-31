package com.aga.data.data.model.user

data class UserResponse(
    val message: String,
    val code: Int,
    val data: UserDataResponse?
)
