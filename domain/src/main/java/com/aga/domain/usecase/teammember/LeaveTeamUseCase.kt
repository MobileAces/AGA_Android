package com.aga.domain.usecase.teammember

import com.aga.domain.repository.TeamMemberRepository
import javax.inject.Inject

class LeaveTeamUseCase @Inject constructor(
    private val repository: TeamMemberRepository
) {
    suspend operator fun invoke(teamId: String, userId: String): Boolean{
        return repository.leaveTeam(teamId, userId)
    }
}