package com.aga.domain.repository

import com.aga.domain.model.Team

interface TeamRepository {
    suspend fun getTeamInfoByTeamId(teamId: String): Team
}