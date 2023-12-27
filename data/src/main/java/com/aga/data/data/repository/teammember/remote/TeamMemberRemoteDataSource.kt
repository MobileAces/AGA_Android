package com.aga.data.data.repository.teammember.remote

import com.aga.domain.model.TeamMember

interface TeamMemberRemoteDataSource {

    suspend fun getTeamMembersByTeamId(teamId: String): List<TeamMember>
}