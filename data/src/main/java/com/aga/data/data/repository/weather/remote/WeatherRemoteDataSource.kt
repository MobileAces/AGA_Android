package com.aga.data.data.repository.weather.remote

import com.aga.domain.model.Weather

interface WeatherRemoteDataSource {
    suspend fun getWeatherInfo(lat: Double, long: Double): Weather
}