package com.aga.domain.usecase.user

import com.aga.domain.repository.UserRepository
import javax.inject.Inject

class NicknameDuplicatedUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(nickname: String): Boolean{
        return repository.isDuplicatedNickname(nickname)
    }
}