package com.aga.domain.repository

import com.aga.domain.model.Team

interface TeamRepository {
    suspend fun getTeamInfoByTeamId(teamId: String): Team

    suspend fun modifyTeamInfo(team: Team): Boolean

    suspend fun deleteTeam(teamId: String): Boolean

    suspend fun createTeam(teamName: String, teamInfo: String, teamMaster: String): Boolean
}