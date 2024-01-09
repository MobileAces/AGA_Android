package com.aga.data.data.api

import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.alarm.AlarmDetailResponse
import com.aga.data.data.model.alarmdetail.AlarmDetailModifyRequest
import com.aga.data.data.model.alarmdetail.AlarmDetailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AlarmDetailService {
    @POST("personalAlarms")
    suspend fun createNewPersonalAlarm(
        @Body alarmDetailRequest: AlarmDetailRequest
    ): Response<DefaultResponse<AlarmDetailResponse>>

    @PUT("personalAlarms")
    suspend fun modifyPersonalAlarm(
        @Body alarmDetailModifyRequest: AlarmDetailModifyRequest
    ): Response<DefaultResponse<AlarmDetailResponse>>
}