package com.aga.domain.repository

import com.aga.domain.model.TeamMember

interface TeamMemberRepository {
    suspend fun getTeamMembersByTeamId(teamId: String): List<TeamMember>
}