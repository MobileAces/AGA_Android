package com.aga.data.data.model.mapper

import com.aga.data.data.model.alarm.AlarmDataResponse
import com.aga.data.data.model.member.TeamMemberDataResponse
import com.aga.data.data.model.team.TeamInfoChangeDataResponse
import com.aga.data.data.model.team.TeamResponse
import com.aga.data.data.model.teamlist.TeamByUserResponse
import com.aga.data.data.model.user.UserResponse
import com.aga.domain.model.Alarm
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamWithMember
import com.aga.domain.model.User
import java.time.DayOfWeek

fun TeamResponse.toTeam(): Team {
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

fun TeamInfoChangeDataResponse.toTeam(): Team{
    return Team(
        this.teamId,
        "",
        this.teamName,
        this.teamInfo,
        ""
    )
}

fun List<TeamByUserResponse>.toTeamWithMemberList(): List<TeamWithMember>{
    return this.map {teamByUserResponse ->
        TeamWithMember(
            teamByUserResponse.teamId,
            teamByUserResponse.teamName,
            teamByUserResponse.teamInfo,
            teamByUserResponse.teamMaster,
            teamByUserResponse.userLists.toTeamMemberList()
        )
    }
}

fun UserResponse.toUser(): User{
    return User(
        this.userId,
        "",
        this.userNickname,
        this.userPhone
    )
}

fun AlarmDataResponse.toAlarm(): Alarm {
    return Alarm(
        alarmId,
        alarmName,
        alarmDayStringToDayofWeekSet(alarmDay),
        teamId
    )
}

/**
 * @param string 반드시 MON,TUE,WED,THU,FRI,SAT,SUN (영문 요일 앞3자, 사이 쉼표) 형식이어야합니다.
 */
private fun alarmDayStringToDayofWeekSet(string: String): Set<DayOfWeek> {
    val dayOfWeek = DayOfWeek.values()
    return string.split(",").map { dayString ->
        dayOfWeek.find { day ->
            day.toString().contains(dayString)
        } ?: return emptySet()
    }.toSet()
}