package com.aga.data.data.model.mapper

import com.aga.data.data.model.alarmdetail.AlarmDetailEntity
import com.aga.data.data.model.alarmdetail.AlarmDetailModifyRequest
import com.aga.data.data.model.alarmdetail.AlarmDetailRequest
import com.aga.data.data.model.member.AuthorityChangeRequest
import com.aga.data.data.model.statistics.WakeUpRegisterRequest
import com.aga.data.data.model.user.join.JoinRequest
import com.aga.data.data.model.user.login.LoginRequest
import com.aga.data.data.model.team.TeamInfoChangeRequest
import com.aga.data.data.model.user.update.UserUpdateRequest
import com.aga.domain.model.AlarmDetail
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamMemberWithTeam
import com.aga.domain.model.User
import com.aga.domain.model.WakeUp
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

fun TeamMemberWithTeam.toAuthorityChangeRequest(): AuthorityChangeRequest{
    return AuthorityChangeRequest(
        this.teamId,
        this.userId,
        this.authority
    )
}

fun AlarmDetail.toAlarmDetailRequest(): AlarmDetailRequest{
    return AlarmDetailRequest(
        hour,
        minute,
        repeatTime,
        memo,
        forecast,
        memoVoice,
        isOn,
        alarmId,
        userId
    )
}

fun AlarmDetail.toAlarmDetailModifyRequest(): AlarmDetailModifyRequest{
    return AlarmDetailModifyRequest(
        id,
        hour,
        minute,
        repeatTime,
        memo,
        forecast,
        memoVoice,
        isOn
    )
}

fun AlarmDetail.toAlarmDetailEntity(): AlarmDetailEntity {
    return AlarmDetailEntity(
        dayOfWeek,
        id,
        hour,
        minute,
        repeatTime,
        memo,
        forecast,
        memoVoice,
        isOn,
        alarmId,
        userId,
        userNickname,
        isVibrateOn,
        isRingtoneOn,
        ringtoneUri
    )
}

fun WakeUp.toWakeUpRegisterRequest(): WakeUpRegisterRequest{
    return WakeUpRegisterRequest(
        this.success,
        this.dateTime,
        this.wakeupHour,
        this.wakeupMinute,
        this.wakeupMemo,
        this.wakeupForecast,
        this.wakeupVoice,
        this.userId,
        this.teamId,
        this.alarmId,
        this.alarmDetailId
    )
}