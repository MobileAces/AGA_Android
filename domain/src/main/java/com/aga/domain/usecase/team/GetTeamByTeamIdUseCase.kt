package com.aga.domain.usecase.team

import com.aga.domain.model.Team
import com.aga.domain.repository.TeamRepository
import javax.inject.Inject

class GetTeamByTeamIdUseCase @Inject constructor(
    private val repository: TeamRepository
) {

    suspend operator fun invoke(teamId: String): Team{
        return repository.getTeamInfoByTeamId(teamId)
    }
}