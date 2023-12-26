package com.aga.data.data.model.mapper

import com.aga.data.data.model.join.JoinDataResponse
import com.aga.data.data.model.login.LoginDataResponse
import com.aga.data.data.model.login.LoginResponse
import com.aga.data.data.model.member.TeamMemberDataResponse
import com.aga.data.data.model.team.TeamDataResponse
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
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

fun TeamDataResponse.toTeam(): Team {
    return Team(
        this.teamId,
        this.teamCreateDate,
        this.teamName,
        this.teamInfo,
        this.teamMaster
    )
}

fun List<TeamMemberDataResponse>.toTeamMemberList(): List<TeamMember>{
    return this.map {
        TeamMember(
            it.userId,
            it.userNickname,
            it.authority
        )
    }
}