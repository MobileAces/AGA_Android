package com.aga.data.data.model.user

data class PasswordChangeResponse(
    val message: String,
    val code: Int,
    val data: Boolean
)
