package com.aga.data.data.api

import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.team.TeamCreateRequest
import com.aga.data.data.model.team.TeamInfoChangeRequest
import com.aga.data.data.model.team.TeamInfoChangeResponse
import com.aga.data.data.model.team.TeamResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TeamService {
    @GET("teams/{teamId}")
    suspend fun getTeamInfo(
        @Path(value = "teamId") teamId: String
    ): Response<DefaultResponse<TeamResponse>>

    @PUT("teams")
    suspend fun modifyTeamInfo(
        @Body teamInfo: TeamInfoChangeRequest
    ): TeamInfoChangeResponse

    @DELETE("teams/{teamId}")
    suspend fun deleteTeam(
        @Path(value = "teamId") teamId: String
    ): String

    @POST("teams")
    suspend fun createTeam(
        @Body teamCreateRequest: TeamCreateRequest
    ): Response<TeamResponse>
}