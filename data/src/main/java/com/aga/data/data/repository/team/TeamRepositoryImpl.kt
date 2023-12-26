package com.aga.data.data.repository.team

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

}