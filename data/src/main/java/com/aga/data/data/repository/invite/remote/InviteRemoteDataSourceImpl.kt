package com.aga.data.data.repository.invite.remote

import com.aga.data.data.api.InviteService
import com.aga.data.data.model.invite.ConfirmCodeRequest
import javax.inject.Inject

class InviteRemoteDataSourceImpl @Inject constructor(
    private val inviteService: InviteService
): InviteRemoteDataSource {
    override suspend fun getInviteCode(teamId: Int): String {
        val response = inviteService.getInviteCode(teamId)
        return if (response.isSuccessful)
            response.body()?.data!!.inviteCode
        else
            "CODE_FAIL"
    }

    override suspend fun confirmInviteCode(inviteCode: String): Int {
        val response = inviteService.confirmInviteCode(ConfirmCodeRequest(inviteCode))
        return if (response.isSuccessful)
            response.body()?.data!!.teamId
        else
            -1
    }
}