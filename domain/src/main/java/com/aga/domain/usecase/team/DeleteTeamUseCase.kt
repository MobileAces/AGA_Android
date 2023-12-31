package com.aga.domain.usecase.team

import com.aga.domain.repository.TeamRepository
import javax.inject.Inject

class DeleteTeamUseCase @Inject constructor(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(teamId: String): Boolean{
        return repository.deleteTeam(teamId)
    }
}