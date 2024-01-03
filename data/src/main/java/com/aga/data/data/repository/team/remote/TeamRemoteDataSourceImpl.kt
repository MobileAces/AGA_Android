package com.aga.data.data.repository.team.remote

import com.aga.data.data.api.TeamService
import com.aga.data.data.model.mapper.toTeam
import com.aga.data.data.model.mapper.toTeamInfoChangeRequest
import com.aga.data.data.model.team.TeamCreateRequest
import com.aga.domain.model.Team
import javax.inject.Inject

class TeamRemoteDataSourceImpl @Inject constructor(
    private val teamService: TeamService
): TeamRemoteDataSource {
    override suspend fun getTeamInfoByTeamId(teamId: String): Team {
        val response = teamService.getTeamInfo(teamId)
        return if (response.isSuccessful){
            response.body()?.data!!.toTeam()
        }else{
            Team("FAIL", "", "", "", "")
        }
    }

    override suspend fun modifyTeamInfo(team: Team): Boolean {
        val response = teamService.modifyTeamInfo(team.toTeamInfoChangeRequest())
        return response.code == 200
    }

    override suspend fun deleteTeam(teamId: String): Boolean {
        return teamService.deleteTeam(teamId) == "Success"
    }

    override suspend fun createTeam(teamCreateRequest: TeamCreateRequest): Boolean {
        return teamService.createTeam(teamCreateRequest).isSuccessful
    }

}