package com.aga.data.data.repository.invite.remote

interface InviteRemoteDataSource {
    suspend fun getInviteCode(teamId: Int):String

    suspend fun confirmInviteCode(inviteCode: String): Int
}