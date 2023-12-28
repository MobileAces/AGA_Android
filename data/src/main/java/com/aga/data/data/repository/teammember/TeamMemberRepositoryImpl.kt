package com.aga.data.data.repository.teammember

import com.aga.data.data.repository.teammember.remote.TeamMemberRemoteDataSource
import com.aga.domain.model.TeamMember
import com.aga.domain.model.TeamWithMember
import com.aga.domain.repository.TeamMemberRepository
import javax.inject.Inject

class TeamMemberRepositoryImpl @Inject constructor(
    private val remoteDataSource: TeamMemberRemoteDataSource
) : TeamMemberRepository{
    override suspend fun getTeamMembersByTeamId(teamId: String): List<TeamMember> {
        return remoteDataSource.getTeamMembersByTeamId(teamId)
    }

    override suspend fun getTeamMemberByUserId(userId: String): List<TeamWithMember> {
        return remoteDataSource.getTeamMemberByUserId(userId)
    }
}