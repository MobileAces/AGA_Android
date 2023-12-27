package com.aga.domain.usecase.teammember

import com.aga.domain.model.TeamMember
import com.aga.domain.repository.TeamMemberRepository
import javax.inject.Inject

class GetTeamMembersByTeamIdUseCase @Inject constructor(
    private val repository: TeamMemberRepository
){
    suspend operator fun invoke(teamId: String): List<TeamMember>{
        return repository.getTeamMembersByTeamId(teamId)
    }
}