package com.aga.domain.repository

interface InviteRepository {

    suspend fun getInviteCode(teamId: Int): String

    suspend fun confirmInviteCode(inviteCode: String): Int
}