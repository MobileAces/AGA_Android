package com.aga.domain.usecase.team

import com.aga.domain.repository.TeamRepository
import javax.inject.Inject

class GetTeamByTeamIdUseCase @Inject constructor(
    private val repository: TeamRepository
) {
}