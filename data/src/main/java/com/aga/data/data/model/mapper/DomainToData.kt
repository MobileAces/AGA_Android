package com.aga.data.data.model.mapper

import com.aga.data.data.model.join.JoinRequest
import com.aga.data.data.model.login.LoginRequest
import com.aga.domain.model.User

fun User.toLoginRequest(): LoginRequest{
    return LoginRequest(
        this.id,
        this.pw
    )
}

fun User.toJoinRequest(): JoinRequest{
    return JoinRequest(
        this.id,
        this.pw,
        this.nickname,
        this.phone
    )
}