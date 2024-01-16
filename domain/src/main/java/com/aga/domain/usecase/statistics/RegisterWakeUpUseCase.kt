package com.aga.domain.usecase.statistics

import com.aga.domain.model.WakeUp
import com.aga.domain.repository.StatisticsRepository
import javax.inject.Inject

class RegisterWakeUpUseCase @Inject constructor(
    private val repository: StatisticsRepository
) {
    suspend operator fun invoke(wakeUp: WakeUp): Boolean{
        return repository.registerWakeUp(wakeUp)
    }
}