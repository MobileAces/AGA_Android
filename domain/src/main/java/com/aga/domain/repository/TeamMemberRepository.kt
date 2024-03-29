package com.aga.domain.repository

import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamMemberWithTeam
import com.aga.domain.model.TeamWithMember

interface TeamMemberRepository {
    suspend fun getTeamMembersByTeamId(teamId: String): List<TeamMember>

    suspend fun leaveTeam(teamId: Int, userId: String): Boolean

    suspend fun getTeamMemberByUserId(userId: String): List<TeamWithMember>

    suspend fun modifyAuthority(teamMemberWithTeam: TeamMemberWithTeam): Boolean

    suspend fun registerTeamMember(teamId: Int, userId: String): Boolean
}