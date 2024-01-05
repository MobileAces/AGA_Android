package com.aga.data.data.repository.team

import com.aga.data.data.model.team.TeamCreateRequest
import com.aga.data.data.repository.team.remote.TeamRemoteDataSource
import com.aga.domain.model.Team
import com.aga.domain.repository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(
    private val remoteDataSource: TeamRemoteDataSource
): TeamRepository {
    override suspend fun getTeamInfoByTeamId(teamId: String): Team {
        return remoteDataSource.getTeamInfoByTeamId(teamId)
    }

    override suspend fun modifyTeamInfo(team: Team): Boolean {
        return remoteDataSource.modifyTeamInfo(team)
    }

    override suspend fun deleteTeam(teamId: Int): Boolean {
        return remoteDataSource.deleteTeam(teamId)
    }

    override suspend fun createTeam(teamName: String, teamInfo: String, teamMaster: String): Boolean {
        return remoteDataSource.createTeam(
            TeamCreateRequest(teamName, teamInfo, teamMaster)
        )
    }
}