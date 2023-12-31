package com.aga.data.data.repository.team.remote

import com.aga.domain.model.Team

interface TeamRemoteDataSource {
    suspend fun getTeamInfoByTeamId(teamId: String): Team

    suspend fun modifyTeamInfo(team: Team): Boolean

    suspend fun deleteTeam(teamId: String): Boolean
}