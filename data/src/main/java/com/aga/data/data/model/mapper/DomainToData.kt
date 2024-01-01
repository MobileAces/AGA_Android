package com.aga.data.data.model.mapper

import com.aga.data.data.model.join.JoinRequest
import com.aga.data.data.model.login.LoginRequest
import com.aga.data.data.model.team.TeamInfoChangeRequest
import com.aga.data.data.model.user.UserUpdateRequest
import com.aga.domain.model.Team
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

fun Team.toTeamInfoChangeRequest(): TeamInfoChangeRequest{
    return TeamInfoChangeRequest(
        this.teamId,
        this.teamName,
        this.teamInfo,
        ""
    )
}

fun User.toUserUpdateRequest(): UserUpdateRequest{
    return UserUpdateRequest(
        this.id,
        this.nickname,
        this.phone
    )
}