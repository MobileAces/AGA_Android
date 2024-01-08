package com.aga.domain.usecase.invite

import com.aga.domain.repository.InviteRepository
import javax.inject.Inject

class ConfirmInviteCodeUseCase @Inject constructor(
    private val repository: InviteRepository
) {
    suspend operator fun invoke(inviteCode: String): Int{
        return repository.confirmInviteCode(inviteCode)
    }
}