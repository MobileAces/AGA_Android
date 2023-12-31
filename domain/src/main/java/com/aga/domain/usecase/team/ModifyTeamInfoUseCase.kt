package com.aga.domain.usecase.team

import com.aga.domain.model.Team
import com.aga.domain.repository.TeamRepository
import javax.inject.Inject

class ModifyTeamInfoUseCase @Inject constructor(
    private val repository: TeamRepository
) {
    suspend operator fun invoke(team: Team): Boolean{
        return repository.modifyTeamInfo(team)
    }
}