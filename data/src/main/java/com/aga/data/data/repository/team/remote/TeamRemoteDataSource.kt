package com.aga.data.data.repository.team.remote

import com.aga.domain.model.Team

interface TeamRemoteDataSource {
    suspend fun getTeamInfoByTeamId(teamId: String): Team
}