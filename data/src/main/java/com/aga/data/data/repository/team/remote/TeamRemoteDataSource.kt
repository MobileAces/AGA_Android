package com.aga.data.data.repository.team.remote

import com.aga.data.data.model.team.TeamCreateRequest
import com.aga.domain.model.Team

interface TeamRemoteDataSource {
    suspend fun getTeamInfoByTeamId(teamId: String): Team

    suspend fun modifyTeamInfo(team: Team): Boolean

    suspend fun deleteTeam(teamId: Int): Boolean

    suspend fun createTeam(teamCreateRequest: TeamCreateRequest): Boolean
}