package com.aga.data.data.repository.weather

import com.aga.data.data.repository.weather.remote.WeatherRemoteDataSource
import com.aga.domain.model.Weather
import com.aga.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun getWeatherInfo(lat: Double, long: Double): Weather {
        return remoteDataSource.getWeatherInfo(lat, long)
    }
}