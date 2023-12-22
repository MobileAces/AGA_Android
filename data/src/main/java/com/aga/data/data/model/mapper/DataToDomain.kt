package com.aga.data.data.model.mapper

import com.aga.data.data.model.user.UserResponse
import com.aga.domain.model.User

fun UserResponse.toUser(): User{
    return User(
        this.userId,
        "",
        "",
        ""
    )
}