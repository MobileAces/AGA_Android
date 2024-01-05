package com.aga.data.data.model.mapper

import com.aga.data.data.model.member.AuthorityChangeRequest
import com.aga.data.data.model.user.join.JoinRequest
import com.aga.data.data.model.user.login.LoginRequest
import com.aga.data.data.model.team.TeamInfoChangeRequest
import com.aga.data.data.model.user.update.UserUpdateRequest
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamMemberWithTeam
import com.aga.domain.model.User

fun User.toLoginRequest(): LoginRequest {
    return LoginRequest(
        this.id,
        this.pw
    )
}

fun User.toJoinRequest(): JoinRequest {
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
        this.teamMaster
    )
}

fun User.toUserUpdateRequest(): UserUpdateRequest {
    return UserUpdateRequest(
        this.id,
        this.nickname,
        this.phone
    )
}

fun TeamMemberWithTeam.toAuthorityChangeRequest(): AuthorityChangeRequest{
    return AuthorityChangeRequest(
        this.teamId,
        this.userId,
        this.authority
    )
}