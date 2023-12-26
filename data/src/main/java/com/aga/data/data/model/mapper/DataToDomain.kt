package com.aga.data.data.model.mapper

import com.aga.data.data.model.join.JoinDataResponse
import com.aga.data.data.model.login.LoginDataResponse
import com.aga.data.data.model.login.LoginResponse
import com.aga.domain.model.User

fun LoginDataResponse.toUser(): User{
    return User(
        this.userId,
        "",
        "",
        ""
    )
}

fun JoinDataResponse.toUser(): User{
    return User(
        this.userId,
        "",
        this.userNickname,
        this.userPhone
    )
}