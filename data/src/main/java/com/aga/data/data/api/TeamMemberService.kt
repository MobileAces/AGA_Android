package com.aga.data.data.api

import com.aga.data.data.model.member.TeamMemberResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamMemberService {
    @GET("members/users")
    suspend fun getTeamMembersByTeamId(
        @Query("id") teamId: String
    ) : TeamMemberResponse
}