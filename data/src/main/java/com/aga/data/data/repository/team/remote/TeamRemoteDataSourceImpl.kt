package com.aga.data.data.repository.team.remote

import com.aga.data.data.api.TeamService
import com.aga.data.data.model.mapper.toTeam
import com.aga.domain.model.Team
import javax.inject.Inject

class TeamRemoteDataSourceImpl @Inject constructor(
    private val teamService: TeamService
): TeamRemoteDataSource {
    override suspend fun getTeamInfoByTeamId(teamId: String): Team {
        val response = teamService.getTeamInfo(teamId)
        return if (response.code == 200){
            response.data!!.toTeam()
        }else{
            Team("FAIL", "", "", "", "")
        }
    }

}