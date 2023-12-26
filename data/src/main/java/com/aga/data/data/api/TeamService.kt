package com.aga.data.data.api

import com.aga.data.data.model.team.TeamResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TeamService {
    @GET("teams/{teamId}")
    suspend fun getTeamInfo(
        @Path(value = "teamId") teamId: String
    ): TeamResponse
}