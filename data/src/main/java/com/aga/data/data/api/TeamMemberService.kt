package com.aga.data.data.api

import com.aga.data.data.model.member.TeamMemberResponse
import com.aga.data.data.model.teamlist.TeamListByUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamMemberService {
    @GET("members/users")
    suspend fun getTeamMembersByTeamId(
        @Query("id") teamId: String
    ) : TeamMemberResponse

    @GET("members/team")
    suspend fun getTeamMemberByUserId(
        @Query("id") userId: String
    ) : TeamListByUserResponse
}