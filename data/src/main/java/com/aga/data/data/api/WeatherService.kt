package com.aga.data.data.api

import com.aga.data.data.model.weather.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeatherInfo(
        @Query(value = "latitude") lat: Double,
        @Query(value = "longitude") long: Double
    ): Response<WeatherResponse>
}