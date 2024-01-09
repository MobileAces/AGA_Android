package com.aga.data.data.api

import com.aga.data.data.model.DefaultResponse
import com.aga.data.data.model.invite.ConfirmCodeRequest
import com.aga.data.data.model.invite.ConfirmCodeResponse
import com.aga.data.data.model.invite.InviteCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface InviteService {
    @POST("invite/creation")
    suspend fun getInviteCode(
        @Query("teamId") teamId: Int
    ): Response<DefaultResponse<InviteCodeResponse>>

    @POST("invite/confirmation")
    suspend fun confirmInviteCode(
        @Body confirmCodeRequest: ConfirmCodeRequest
    ): Response<DefaultResponse<ConfirmCodeResponse>>
}