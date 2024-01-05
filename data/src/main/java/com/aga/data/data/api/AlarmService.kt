package com.aga.data.data.api

import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.alarm.AlarmDataRequest
import com.aga.data.data.model.alarm.AlarmDataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AlarmService {
    @POST("alarms")
    suspend fun createNewAlarm(
        @Body alarmDataRequest: AlarmDataRequest
    ): Response<DefaultResponse<AlarmDataResponse>>


}