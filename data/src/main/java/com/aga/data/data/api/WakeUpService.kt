package com.aga.data.data.api

import com.aga.data.data.model.DefaultListResponse
import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.statistics.PeriodStatisticsRequest
import com.aga.data.data.model.statistics.StatisticsDailyData
import com.aga.data.data.model.statistics.StatisticsPeriodData
import com.aga.data.data.model.statistics.WakeUpRegisterRequest
import com.aga.data.data.model.statistics.WakeUpRegisterData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WakeUpService {
    @POST("wakeup/statistics")
    suspend fun getPeriodStatistics(
        @Body periodStatisticsRequest: PeriodStatisticsRequest
    ): Response<DefaultResponse<StatisticsPeriodData>>

    @GET("wakeup/status")
    suspend fun getDailyStatistics(
        @Query("teamId") teamId: Int,
        @Query("date") date: String
    ): Response<DefaultListResponse<StatisticsDailyData>>

    @POST("wakeup/register")
    suspend fun registerWakeUp(
        @Body wakeUpRegisterRequest: WakeUpRegisterRequest
    ): Response<DefaultResponse<WakeUpRegisterData>>
}