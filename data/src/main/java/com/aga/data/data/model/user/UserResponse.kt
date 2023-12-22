package com.aga.data.data.model.user

data class UserResponse(
    val userId: String,
    val userPw: String?,
    val userNickname: String?,
    val userPhone: String?
)
