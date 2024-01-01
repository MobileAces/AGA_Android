package com.aga.data.data.model.user

data class UserUpdateRequest(
    val userId: String,
    val userNickname: String,
    val userPhone: String
)
