package com.aga.domain.usecase.teammember

import com.aga.domain.repository.TeamMemberRepository
import javax.inject.Inject

class RegisterTeamMemberUseCase @Inject constructor(
    private val repository: TeamMemberRepository
) {
    suspend operator fun invoke(teamInt: Int, userId: String): Boolean{
        return repository.registerTeamMember(teamInt, userId)
    }
}