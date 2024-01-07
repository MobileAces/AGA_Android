package com.aga.data.data.api

import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.statistics.PeriodStatisticsRequest
import com.aga.data.data.model.statistics.StatisticsPeriodData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WakeUpService {
    @POST("wakeup/statistics")
    suspend fun getPeriodStatistics(
        @Body periodStatisticsRequest: PeriodStatisticsRequest
    ): Response<DefaultResponse<StatisticsPeriodData>>
}