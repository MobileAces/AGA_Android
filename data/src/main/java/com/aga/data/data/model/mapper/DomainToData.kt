package com.aga.data.data.model.mapper

import com.aga.data.data.model.user.join.JoinRequest
import com.aga.data.data.model.user.login.LoginRequest
import com.aga.data.data.model.team.TeamInfoChangeRequest
import com.aga.data.data.model.user.update.UserUpdateRequest
import com.aga.domain.model.Team
import com.aga.domain.model.User
import java.time.DayOfWeek

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
        ""
    )
}

fun User.toUserUpdateRequest(): UserUpdateRequest {
    return UserUpdateRequest(
        this.id,
        this.nickname,
        this.phone
    )
}

/**
 * @return MON,TUE,WED,THU,FRI,SAT,SUN (영문 요일 앞3자, 사이 쉼표) 형식
 */
fun Set<DayOfWeek>.toFormattedString(): String {
    return if (this.isNotEmpty()) {
        this
            .map {
                it.toString().substring(0, 3)
            }
            .toString()
            .let {
                it.substring(1, it.length - 1).replace(" ","")
            }
    } else {
        ""
    }
}