package com.aga.data.data.model.user

data class PasswordChangeRequest (
    val userId: String,
    val prePw: String,
    val newPw: String
)
