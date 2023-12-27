package com.aga.data.data.repository.teammember.remote

import com.aga.data.data.api.TeamMemberService
import com.aga.data.data.model.mapper.toTeamMemberList
import com.aga.data.data.model.member.TeamMemberResponse
import com.aga.domain.model.TeamMember
import javax.inject.Inject

class TeamMemberRemoteDataSourceImpl @Inject constructor(
    private val teamMemberService: TeamMemberService
) : TeamMemberRemoteDataSource {
    override suspend fun getTeamMembersByTeamId(teamId: String): List<TeamMember> {
        val response = teamMemberService.getTeamMembersByTeamId(teamId)
        return if (response.code == 200){
            response.dataList.toTeamMemberList()
        }else{
            listOf()
        }
    }
}