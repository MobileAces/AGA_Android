package com.aga.domain.usecase.teammember

import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamWithMember
import com.aga.domain.repository.TeamMemberRepository
import javax.inject.Inject

class GetTeamListByUserIdUseCase @Inject constructor(
    private val repository: TeamMemberRepository
){
    suspend operator fun invoke(userId: String): List<TeamWithMember>{
        return repository.getTeamMemberByUserId(userId)
    }
}