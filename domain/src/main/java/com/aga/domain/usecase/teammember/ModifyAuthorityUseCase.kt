package com.aga.domain.usecase.teammember

import com.aga.domain.model.TeamMemberWithTeam
import com.aga.domain.repository.TeamMemberRepository
import javax.inject.Inject

class ModifyAuthorityUseCase @Inject constructor(
    private val repository: TeamMemberRepository
) {
    suspend operator fun invoke(teamMemberWithTeam: TeamMemberWithTeam): Boolean{
        return repository.modifyAuthority(teamMemberWithTeam)
    }
}