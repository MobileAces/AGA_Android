package com.aga.data.data.api

import com.aga.data.data.model.DefaultBooleanData
import com.aga.data.data.model.DefaultListResponse
import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.alarm.AlarmDataResponse
import com.aga.data.data.model.alarm.AlarmWithDetailListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AlarmService {
    @POST("alarms")
    suspend fun createNewAlarm(
        @Body alarmDataRequest: AlarmDataRequest
    ): Response<DefaultResponse<AlarmDataResponse>>

    @GET("alarms/{teamId}")
    suspend fun getAlarmListByTeamId(
        @Path("teamId") teamId: Int
    ): Response<DefaultListResponse<AlarmWithDetailListResponse>>

    @DELETE("alarms/{alarmId}")
    suspend fun deleteAlarmById(
        @Path("alarmId") id: Int
    ): Response<DefaultResponse<DefaultBooleanData>>
}