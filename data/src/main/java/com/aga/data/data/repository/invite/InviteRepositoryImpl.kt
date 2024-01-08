package com.aga.data.data.repository.invite

import com.aga.data.data.repository.invite.remote.InviteRemoteDataSource
import com.aga.domain.repository.InviteRepository
import javax.inject.Inject

class InviteRepositoryImpl @Inject constructor(
    private val remoteDataSource: InviteRemoteDataSource
): InviteRepository {
    override suspend fun getInviteCode(teamId: Int): String {
        return remoteDataSource.getInviteCode(teamId)
    }

    override suspend fun confirmInviteCode(inviteCode: String): Int {
        return remoteDataSource.confirmInviteCode(inviteCode)
    }
}