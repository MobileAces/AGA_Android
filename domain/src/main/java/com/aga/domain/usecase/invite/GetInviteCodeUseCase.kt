package com.aga.domain.usecase.invite

import com.aga.domain.repository.InviteRepository
import javax.inject.Inject

class GetInviteCodeUseCase @Inject constructor(
    private val repository: InviteRepository
){
    suspend operator fun invoke(teamId: Int): String{
        return repository.getInviteCode(teamId)
    }
}