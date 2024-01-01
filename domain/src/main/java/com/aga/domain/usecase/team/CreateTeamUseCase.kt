package com.aga.domain.usecase.team

import com.aga.domain.repository.TeamRepository
import javax.inject.Inject

class CreateTeamUseCase @Inject constructor(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(teamName: String, teamInfo: String, teamMaster: String): Boolean{
        return repository.createTeam(teamName, teamInfo, teamMaster)
    }
}