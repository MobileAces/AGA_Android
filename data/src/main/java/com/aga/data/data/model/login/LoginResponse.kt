package com.aga.data.data.model.login

import com.aga.data.data.model.join.JoinDataResponse

data class LoginResponse(
    val message: String,
    val code: Int,
    val data: LoginDataResponse?
)
