package com.aga.data.data.model.user.update

data class UserUpdateRequest(
    val userId: String,
    val userNickname: String,
    val userPhone: String
)
