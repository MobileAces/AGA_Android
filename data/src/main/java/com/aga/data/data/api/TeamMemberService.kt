package com.aga.data.data.api

import com.aga.data.data.model.DefaultBooleanData
import com.aga.data.data.model.DefaultListResponse
import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.member.AuthorityChangeRequest
import com.aga.data.data.model.member.AuthorityChangeResponse
import com.aga.data.data.model.member.TeamMemberResponse
import com.aga.data.data.model.teamlist.TeamByUserResponse
import retrofit2.http.DELETE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface TeamMemberService {
    @GET("members/users")
    suspend fun getTeamMembersByTeamId(
        @Query("id") teamId: String
    ) : Response<TeamMemberResponse>

    @DELETE("members/{teamId}/{userId}")
    suspend fun deleteTeamMember(
        @Path(value = "teamId") teamId: Int,
        @Path(value = "userId") userId: String
    ): Response<DefaultResponse<DefaultBooleanData>>

    @GET("members/teams")
    suspend fun getTeamMemberByUserId(
        @Query("id") userId: String
    ) : Response<DefaultListResponse<TeamByUserResponse>>

    @PUT("members")
    suspend fun modifyAuthority(
        @Body authorityChangeRequest: AuthorityChangeRequest
    ): Response<DefaultResponse<AuthorityChangeResponse>>
}