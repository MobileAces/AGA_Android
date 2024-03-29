package com.aga.data.data.repository.teammember.remote

import android.util.Log
import com.aga.data.data.api.TeamMemberService
import com.aga.data.data.model.mapper.toAuthorityChangeRequest
import com.aga.data.data.model.mapper.toTeamMemberList
import com.aga.data.data.model.mapper.toTeamWithMemberList
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamMemberWithTeam
import com.aga.domain.model.TeamWithMember
import javax.inject.Inject

class TeamMemberRemoteDataSourceImpl @Inject constructor(
    private val teamMemberService: TeamMemberService
) : TeamMemberRemoteDataSource {
    override suspend fun getTeamMembersByTeamId(teamId: String): List<TeamMember> {
        val response = teamMemberService.getTeamMembersByTeamId(teamId)
        return if (response.code() == 200){
            response.body()?.dataList!!.toTeamMemberList()
        }else{
            listOf()
        }
    }

    override suspend fun getTeamMemberByUserId(userId: String): List<TeamWithMember> {
        val response = teamMemberService.getTeamMemberByUserId(userId)
        return if (response.isSuccessful && response.body() != null){
            response.body()?.dataList!!.toTeamWithMemberList()
        } else {
            Log.d("TAG", "getTeamMemberByUserId: ${response.code()}")
            listOf()
        }
    }

    override suspend fun deleteTeamMember(teamId: Int, userId: String): Boolean {
        return teamMemberService.deleteTeamMember(teamId, userId).isSuccessful
    }

    override suspend fun authorityChange(teamMemberWithTeam: TeamMemberWithTeam): Boolean {
        return teamMemberService.modifyAuthority(teamMemberWithTeam.toAuthorityChangeRequest()).isSuccessful
    }

    override suspend fun registerTeamMember(teamId: Int, userId: String): Boolean {
        return teamMemberService.registerTeamMember(teamId, userId).isSuccessful
    }

}