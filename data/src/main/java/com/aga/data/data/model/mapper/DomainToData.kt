package com.aga.data.data.model.mapper

import com.aga.data.data.model.user.UserRequest
import com.aga.domain.model.User

fun User.toUserRequest(): UserRequest{
    return UserRequest(
        this.id,
        this.pw,
        this.nickname,
        this.phone
    )
}