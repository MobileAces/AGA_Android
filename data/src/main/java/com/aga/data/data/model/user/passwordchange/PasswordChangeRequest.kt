package com.aga.data.data.model.user.passwordchange

data class PasswordChangeRequest (
    val userId: String,
    val prePw: String,
    val newPw: String
)
