package com.aga.domain.repository

import com.aga.domain.model.Weather

interface WeatherRepository {
    suspend fun getWeatherInfo(lat: Double, long: Double): Weather
}