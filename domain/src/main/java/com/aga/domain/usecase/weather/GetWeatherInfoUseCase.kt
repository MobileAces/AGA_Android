package com.aga.domain.usecase.weather

import com.aga.domain.model.Weather
import com.aga.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, long: Double): Weather{
        return repository.getWeatherInfo(lat, long)
    }
}