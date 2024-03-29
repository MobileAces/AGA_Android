package com.aga.data.data.model.mapper

import android.util.Log
import com.aga.data.data.model.alarm.AlarmDataResponse
import com.aga.data.data.model.alarm.AlarmDetailResponse
import com.aga.data.data.model.alarm.AlarmWithDetailListResponse
import com.aga.data.data.model.alarmdetail.AlarmDetailEntity
import com.aga.data.data.model.member.TeamMemberDataResponse
import com.aga.data.data.model.statistics.StatisticsDailyData
import com.aga.data.data.model.statistics.StatisticsDailyDetail
import com.aga.data.data.model.statistics.StatisticsPeriodData
import com.aga.data.data.model.statistics.StatisticsPeriodUser
import com.aga.data.data.model.team.TeamInfoChangeDataResponse
import com.aga.data.data.model.team.TeamResponse
import com.aga.data.data.model.teamlist.TeamByUserResponse
import com.aga.data.data.model.user.UserResponse
import com.aga.data.data.model.weather.WeatherData
import com.aga.data.data.model.weather.WeatherResponse
import com.aga.domain.model.Alarm
import com.aga.domain.model.DailyStatistics
import com.aga.domain.model.DailyStatisticsDetail
import com.aga.domain.model.PeriodStatistics
import com.aga.domain.model.PeriodStatisticsUser
import com.aga.domain.model.AlarmDetail
import com.aga.domain.model.AlarmWithDetailList
import com.aga.domain.model.Team
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamWithMember
import com.aga.domain.model.User
import com.aga.domain.model.Weather
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

fun StatisticsPeriodData.toPeriodStatistics(): PeriodStatistics{
    return PeriodStatistics(
        this.userList.map {
            it.toPeriodStatisticsUser()
        },
        this.totalSum,
        this.totalSuccessSum
    )
}

fun StatisticsPeriodUser.toPeriodStatisticsUser(): PeriodStatisticsUser{
    return PeriodStatisticsUser(
        this.nickname,
        this.totalSum,
        this.totalSuccessSum
    )
}

fun StatisticsDailyData.toDailyStatistics(): DailyStatistics{
    return DailyStatistics(
        this.alarmId,
        this.alarmName,
        this.wakeupList.map {
            it.toDailyStatisticsDetail()
        }
    )
}

fun StatisticsDailyDetail.toDailyStatisticsDetail(): DailyStatisticsDetail{
    return DailyStatisticsDetail(
        this.userNickname,
        this.success,
        this.datetime,
        this.wakeupHour,
        this.wakeupMinute,
        this.wakeupMemo,
        this.wakeupForecast,
        this.wakeupVoice
    )
}

fun AlarmDetailResponse.toAlarmDetail(): AlarmDetail {
    return AlarmDetail(
        "",
        alarmDetailId,
        alarmDetailHour,
        alarmDetailMinute,
        alarmDetailRetime,
        alarmDetailMemo,
        alarmDetailForecast,
        alarmDetailMemoVoice,
        alarmDetailIsOn,
        alarmId,
        userId,
        userNickname,
        true,
        true,
        "",
        -1
    )
}

fun AlarmDetailEntity.toAlarmDetail(): AlarmDetail{
    Log.d("awesome", "toAlarmDetail: $this")
    return AlarmDetail(
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
        ringtoneUri,
        teamId
    )
}

fun AlarmWithDetailListResponse.toAlarmWithDetailList(): AlarmWithDetailList {
    return AlarmWithDetailList(
        alarmId,
        alarmName,
        alarmDayStringToDayofWeekSet(alarmDay),
        teamId,
        alarmDetailList.map { it.toAlarmDetail() }
    )
}

fun WeatherResponse.toWeather(): Weather{
    return Weather(
        this.weather.rainAmount,
        this.message
    )
}