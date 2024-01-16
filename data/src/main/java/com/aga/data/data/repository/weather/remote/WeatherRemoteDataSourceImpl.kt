package com.aga.data.data.repository.weather.remote

import com.aga.data.data.api.WeatherService
import com.aga.data.data.model.mapper.toWeather
import com.aga.domain.model.Weather
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRemoteDataSource {
    override suspend fun getWeatherInfo(lat: Double, long: Double): Weather {
        val response = weatherService.getWeatherInfo(lat, long)
        return if (response.isSuccessful){
            response.body()!!.toWeather()
        }else
            Weather(-1.0, "FAIL")
    }
}