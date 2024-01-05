package com.aga.data.data.repository.teammember.remote

import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamMemberWithTeam
import com.aga.domain.model.TeamWithMember

interface TeamMemberRemoteDataSource {

    suspend fun getTeamMembersByTeamId(teamId: String): List<TeamMember>

    suspend fun getTeamMemberByUserId(userId: String): List<TeamWithMember>

    suspend fun deleteTeamMember(teamId: Int, userId: String): Boolean

    suspend fun authorityChange(teamMemberWithTeam: TeamMemberWithTeam): Boolean
}