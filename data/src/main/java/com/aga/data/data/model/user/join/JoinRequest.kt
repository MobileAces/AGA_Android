package com.aga.data.data.model.user.join

data class JoinRequest(
    val userId: String,
    val userPw: String,
    val userNickname: String,
    val userPhone: String
)
