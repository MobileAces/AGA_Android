package com.aga.data.data.api

import com.aga.data.data.model.member.TeamMemberResponse
import retrofit2.http.DELETE
import com.aga.data.data.model.teamlist.TeamListByUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamMemberService {
    @GET("members/users")
    suspend fun getTeamMembersByTeamId(
        @Query("id") teamId: String
    ) : TeamMemberResponse

    @DELETE("members/{teamId}/{userId}")
    suspend fun deleteTeamMember(
        @Path(value = "teamId") teamId: String,
        @Path(value = "userId") userId: String
    ): String

    @GET("members/teams")
    suspend fun getTeamMemberByUserId(
        @Query("id") userId: String
    ) : Response<TeamListByUserResponse>
}